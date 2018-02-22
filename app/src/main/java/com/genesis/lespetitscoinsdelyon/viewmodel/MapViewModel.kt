package com.genesis.lespetitscoinsdelyon.viewmodel

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

    private val hospitals = arrayListOf(Hospital("Hotel dieu",  "Clerge"), Hospital("Imothep", "egyptien"))

    var selectedThemes  = BehaviorSubject.create<ArrayList<Item>>()
    var availableThemes: BehaviorSubject<ArrayList<Theme>> = BehaviorSubject.create()

    init {
        var themes = arrayListOf(Theme("new theme", null))
        availableThemes.onNext(themes)
    }

    fun selectTheme() {
        if (selectedThemes.value.size == 0) {
            selectedThemes.value.plus(elements = hospitals.map({ it.convertToItem() }))
        }
    }

    fun deselectTheme() {
        selectedThemes.value.clear()
    }
}


fun Hospital.convertToItem(): Item {
    return Item(this.nom, Theme(this.theme, null), "ici")
}