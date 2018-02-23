package com.genesis.lespetitscoinsdelyon.ihm.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.viewmodel.Item
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var currentMarkersOptions = ArrayList<MarkerOptions>()
    private var currentMarkers = ArrayList<Marker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        MapViewModel.getInstance().selectedThemes
                .skip(1)
                .subscribe({ itemsList ->
            drawPins(itemsList = itemsList)
        })
    }

    private fun drawPins(itemsList: ArrayList<Item>){
        if (mMap != null) {
            mMap.clear()
        }
        itemsList.map {
            val markerOption = markerFromItem(it)
            if (markerOption != null) {
                currentMarkersOptions.add(markerOption)
                currentMarkers.add(mMap.addMarker(markerOption))
            }
        }
    }

    private fun markerFromItem(item: Item): MarkerOptions? {
        var marker: MarkerOptions? = null
        if (item.localisation != null) {

            marker = MarkerOptions().position(item.localisation).title(item.name)
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val bellecour = LatLng(45.759371, 4.832287)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bellecour))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f))


    }
}
