package com.genesis.lespetitscoinsdelyon

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.util.Log
import com.genesis.lespetitscoinsdelyon.`interface`.ILocationChanged


/**
 * Created by hpatural on 23/02/2018.
 */
class LocationUtils(val context: Context, val listener: ILocationChanged) {

    private var locationManager : LocationManager? = null

    val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            listener.onLocationChanged(location)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    init {
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }
    }
}
