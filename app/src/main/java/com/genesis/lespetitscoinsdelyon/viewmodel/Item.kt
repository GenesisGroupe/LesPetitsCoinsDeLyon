package com.genesis.lespetitscoinsdelyon.viewmodel

import com.google.android.gms.maps.model.LatLng

data class Item (val name: String, val theme: Theme, val localisation: LatLng?, val gid: Int)
data class Item2D(val name:String, val theme:Theme, val polygon: ArrayList<LatLng>?, val gid: Int)

enum class Theme(var selected: Boolean) {
    fountains(false),
    security(false),
    hospitals(false)
}