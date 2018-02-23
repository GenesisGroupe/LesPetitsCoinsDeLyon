package com.genesis.lespetitscoinsdelyon.ihm.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.genesis.lespetitscoinsdelyon.LocationUtils
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.`interface`.ILocationChanged
import com.genesis.lespetitscoinsdelyon.viewmodel.Item
import com.genesis.lespetitscoinsdelyon.viewmodel.MapViewModel
import com.genesis.lespetitscoinsdelyon.viewmodel.Theme

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ILocationChanged {


    private lateinit var mMap: GoogleMap
    var currentPosition  : BehaviorSubject<Location> = BehaviorSubject.create()

    companion object {
        val MIN_DISTANCE = 1500
        val MY_PERMISSIONS_REQUEST_LOCATION = 99

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /*MapViewModel.getInstance().selectedThemes.subscribe({ itemsList ->
            drawPins(itemsList = itemsList)
        })*/

        Observables.combineLatest(MapViewModel.getInstance().selectedThemes, currentPosition).skip(1)
                .subscribe({
                    drawPins(it.first, it.second)
                })

        setupPermissions()
        initLocationProvider()


    }

    private fun drawPins(itemsList: ArrayList<Item>, currentLocation: Location){


        itemsList.map {
            if (it.localisation != null) {

                //Filter on distance < 500m from user
                var tempLocation = Location("")
                tempLocation.latitude = it.localisation.latitude
                tempLocation.longitude = it.localisation.longitude

                if (currentPosition == null) {
                    return
                }

                if (tempLocation.distanceTo(currentLocation) < MIN_DISTANCE) {
                    var marker = MarkerOptions().position(it.localisation).title(it.name)
                    when (it.theme) {
                        Theme.hospitals -> {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        Theme.security -> {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        }
                        Theme.fountains -> {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        }
                    }
                    mMap.addMarker(marker)
                }




            }
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val bellecour = LatLng(45.759371, 4.832287)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bellecour))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f))
        
        initLocationProvider()
    }




    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("", "Permission to record denied")
            makeRequest()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
    }


    private fun initLocationProvider () {
        if (isPermissionGranted()) {
            LocationUtils(this, this)

            try {
                mMap.setMyLocationEnabled(true)
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available");
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        initLocationProvider()
    }

    override fun onLocationChanged(location: Location) {
        currentPosition.onNext(location)

        val latLng = LatLng(location.latitude, location.longitude)

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        //mMap.addMarker(MarkerOptions().position(latLng).title("Ma position"))

    }
}
