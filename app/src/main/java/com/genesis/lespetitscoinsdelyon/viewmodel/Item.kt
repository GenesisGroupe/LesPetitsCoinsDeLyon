package com.genesis.lespetitscoinsdelyon.viewmodel

import android.media.Image

data class Item (val name: String, val theme: Theme, val localisation: String)

data class Theme(val name: String, val image: Image?)