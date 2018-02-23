package com.genesis.lespetitscoinsdelyon.`interface`

import android.location.Location

/**
 * Created by hpatural on 23/02/2018.
 */
interface ILocationChanged {
    fun onLocationChanged(location: Location)
}