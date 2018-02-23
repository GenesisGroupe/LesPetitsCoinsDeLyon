package com.genesis.lespetitscoinsdelyon.ihm.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.genesis.lespetitscoinsdelyon.LocationUtils
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.`interface`.ILocationChanged
import com.genesis.lespetitscoinsdelyon.viewmodel.Item
import com.genesis.lespetitscoinsdelyon.viewmodel.Item2D
import com.genesis.lespetitscoinsdelyon.viewmodel.MapViewModel
import com.genesis.lespetitscoinsdelyon.viewmodel.Theme

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import com.google.android.gms.maps.model.PolygonOptions
import kotlin.NoSuchElementException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ILocationChanged {


    private lateinit var mMap: GoogleMap
    var currentPosition  : BehaviorSubject<Location> = BehaviorSubject.create()

    companion object {
        val MIN_DISTANCE = 900
        val MY_PERMISSIONS_REQUEST_LOCATION = 99

    }


    private var currentMarkersOptions = ArrayList<MarkerOptions>()
    private var currentMarkers = ArrayList<Marker>()


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

        Observables.combineLatest(MapViewModel.getInstance().selectedThemes2D, currentPosition).skip(1)
                .subscribe({
                    drawPolygons(it.first, it.second)
                })
    }

    private fun drawPins(itemsList: ArrayList<Item>, currentLocation: Location) {

        if (itemsList.size == 0) {
            mMap.clear()
        }
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
                    var marker = markerFromItem(it)
                    if (marker != null) {
                        // marker is in radius
                        // if it is already drawned, do nothing
                        // if not, add it
                        val markerOption = markerExistOnMap(marker)
                        if (markerOption != null) {
                            // already draw, return
                            return
                        }
                        addMarker(marker)

                    }
                } else {
                    removeMarker(it)
                }
            }
        }
    }

    private fun removeMarker(item: Item) {
        try {
            val drawnedMarker = currentMarkers.first {
                it.title == item.gid.toString()
            }
            drawnedMarker.remove()
            currentMarkers.remove(drawnedMarker)
        } catch(e: Exception) {

        }

    }

    private fun markerExistOnMap(markerOption: MarkerOptions): MarkerOptions? {
        try {
            val alreadyDrawnedPin = currentMarkersOptions.first {
                Log.d("Marker", "title : " + it.title + " - markerOption.title : " + markerOption.title)
                it.title == markerOption.title
            }
            return alreadyDrawnedPin
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    private fun addMarker(markerOption: MarkerOptions) {

        currentMarkersOptions.add(markerOption)
        currentMarkers.add(mMap.addMarker(markerOption))
    }

    private fun drawPolygons(items2Dlist:ArrayList<Item2D>, currentLocation: Location){

        items2Dlist.map {
            if (it.polygon != null && it.polygon.size > 0) {
                //Filter on distance < 500m from user
                var tempLocation = Location("")
                tempLocation.latitude = it.polygon.first().latitude
                tempLocation.longitude = it.polygon.first().longitude

                if (tempLocation.distanceTo(currentLocation) < MIN_DISTANCE) {
                    var polygonOpt = PolygonOptions()
                    polygonOpt.addAll(it.polygon)
                    when (it.theme) {
                        Theme.security -> {
                            polygonOpt.strokeColor(Color.BLUE)
                            polygonOpt.fillColor(Color.parseColor("#880000FF"))
                        }
                    }
                    mMap.addPolygon(polygonOpt)
                }
            }
        }
    }

    private fun markerFromItem(item: Item): MarkerOptions? {
        var marker: MarkerOptions? = null
        if (item.localisation != null) {

            marker = MarkerOptions().position(item.localisation).title(item.gid.toString())
            when (item.theme) {
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
        }
        return marker
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
                if (mMap != null) {
                    mMap.setMyLocationEnabled(true)
                }
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

    }
}
