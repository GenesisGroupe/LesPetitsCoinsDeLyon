package com.genesis.lespetitscoinsdelyon.viewmodel

import com.google.android.gms.maps.model.LatLng
import io.reactivex.subjects.BehaviorSubject
import model.Hospital


class MapViewModel {

    companion object {
        private var shared: MapViewModel = MapViewModel()

        fun getInstance():MapViewModel {
            if (shared == null) {
                shared = MapViewModel()
            }
            return shared
        }
    }

    private val hospitals = arrayListOf(Hospital("Hotel dieu",  "Clerge", LatLng(45.782127, 4.830640)),
            Hospital("Imothep", "egyptien", LatLng(45.749795, 4.835662)))

    var selectedThemes  : BehaviorSubject<ArrayList<Item>> = BehaviorSubject.create()
    var availableThemes : BehaviorSubject<ArrayList<Theme>> = BehaviorSubject.create()

    init {
        var themes = arrayListOf(Theme("new theme", null))
        availableThemes.onNext(themes)

        selectedThemes.onNext(ArrayList<Item>())
    }

    fun selectTheme(theme: Theme) {
        selectedThemes.value.addAll(hospitals.map({ it.convertToItem() }))

    }

    fun deselectTheme() {
        selectedThemes.value.clear()
    }
}


fun Hospital.convertToItem(): Item {
    return Item(this.nom, Theme(this.theme, null), this.coords)
}