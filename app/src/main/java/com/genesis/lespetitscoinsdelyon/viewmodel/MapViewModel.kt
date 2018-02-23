package com.genesis.lespetitscoinsdelyon.viewmodel

import android.app.Application
import android.content.Context
import com.genesis.lespetitscoinsdelyon.MyApplication
import com.google.android.gms.maps.model.LatLng
import dao.FountainDao
import dao.HospitalDao
import io.reactivex.subjects.BehaviorSubject
import model.Fountain
import model.Hospital
import model.Security


class MapViewModel {

    companion object {
        private var shared: MapViewModel = MapViewModel()

        fun getInstance():MapViewModel {
            return shared
        }
    }

    var selectedThemes  : BehaviorSubject<ArrayList<Item>> = BehaviorSubject.create()
    var availableThemes : BehaviorSubject<ArrayList<Theme>> = BehaviorSubject.create()

    init {
        var themes = arrayListOf(Theme.fountains, Theme.security, Theme.hospitals)
        availableThemes.onNext(themes)

        selectedThemes.onNext(ArrayList<Item>())

        var fountainDao = FountainDao(MyApplication.context)
        fountainDao.list

    }

    fun selectTheme(theme: Theme) {
        var items = selectedThemes.value
        var list:List<Item>
        when (theme) {
            Theme.fountains -> {
                val dao = FountainDao(MyApplication.context)
                list = dao.list.map({ it.convertToItem() })
            }
            Theme.security -> {
                list = ArrayList()
            }
            Theme.hospitals -> {
                val dao = HospitalDao(MyApplication.context)
                list = dao.list.map({ it.convertToItem() })
            }
            else -> {
                list = ArrayList()
            }

        }
        items.addAll(list)
        selectedThemes.onNext(items)
    }

    fun unseselectTheme(theme: Theme) {
        var items: ArrayList<Item> = selectedThemes.value

        var themesToDelete = items.map({ item: Item ->
            if (item.theme == theme) return@map item
            else print("go die")
        })

        items.removeAll(themesToDelete)
        selectedThemes.onNext(items)
    }
}


fun Hospital.convertToItem(): Item {
    if (this.nom != null) {
        return Item(this.nom!!, Theme.hospitals, this.the_geom)
    }
    return Item("", Theme.hospitals, null)
}

fun Fountain.convertToItem(): Item {
    if (this.nom != null) {
        return Item(this.nom!!, Theme.fountains, this.the_geom)
    }
    return Item("", Theme.fountains, null)
}


/*
fun Security.convertToItem(): Item {
    if (this.nom != null) {
        return Item(this.nom!!, Theme.security, this.polygon)
    }
}
*/