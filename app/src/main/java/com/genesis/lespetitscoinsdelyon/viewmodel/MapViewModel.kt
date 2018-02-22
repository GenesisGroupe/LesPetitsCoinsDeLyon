package com.genesis.lespetitscoinsdelyon.viewmodel

/**
 * Created by Sylvain on 22/02/2018.
 */

import io.reactivex.Observable
import io.reactivex.rxkotlin;
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import model.Hospital

class MapViewModel {
    val hospitals = arrayListOf(Hospital("Hotel dieu",  "Clerge"), Hospital("Imothep", "egyptien"))

    var selectedThemes = BehaviorSubject.create<ArrayList<Item>>()


    fun getThemes() {
        return "hospitals"
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
    return Item(this.nom, Theme(this.theme, null))
}