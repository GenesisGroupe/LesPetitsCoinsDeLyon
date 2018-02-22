package com.genesis.lespetitscoinsdelyon.viewmodel

import android.media.Image
import com.google.android.gms.maps.model.LatLng

data class Item (val name: String, val theme: Theme, val localisation: LatLng?)

enum class Theme {
    fountains,
    security,
    hospitals
}