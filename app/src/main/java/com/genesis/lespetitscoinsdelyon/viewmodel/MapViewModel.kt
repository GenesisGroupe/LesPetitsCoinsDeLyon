package com.genesis.lespetitscoinsdelyon.viewmodel

import com.genesis.lespetitscoinsdelyon.MyApplication
import dao.FountainDao
import dao.HospitalDao
import dao.SecurityDao
import io.reactivex.subjects.BehaviorSubject
import model.Fountain
import model.Hospital
import model.Security


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

    var selectedThemes  : BehaviorSubject<ArrayList<Item>> = BehaviorSubject.create()
    var selectedThemes2D  : BehaviorSubject<ArrayList<Item2D>> = BehaviorSubject.create()
    var availableThemes : BehaviorSubject<ArrayList<Theme>> = BehaviorSubject.create()

    init {
        var themes = arrayListOf(Theme.fountains, Theme.security, Theme.hospitals)
        availableThemes.onNext(themes)

        selectedThemes.onNext(ArrayList<Item>())
        selectedThemes2D.onNext(ArrayList<Item2D>())

        var fountainDao = FountainDao(MyApplication.context)
        fountainDao.list

    }

    fun selectTheme(theme: Theme) {
        var items = selectedThemes.value
        var items2D = selectedThemes2D.value
        var list:List<Item> = ArrayList<Item>()
        var list2D:List<Item2D> = ArrayList<Item2D>()
        when (theme) {
            Theme.fountains -> {
                val dao = FountainDao(MyApplication.context)
                list = dao.list.map({ it.convertToItem() })
            }
            Theme.security -> {
                val dao = SecurityDao(MyApplication.context)
                list2D = dao.list.map( { it.convertToItem() })
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
        items2D.addAll(list2D)
        selectedThemes.onNext(items)
        selectedThemes2D.onNext(items2D)
    }

    fun unseselectTheme(theme: Theme) {
        var items: ArrayList<Item> = selectedThemes.value
        var items2D: ArrayList<Item2D> = selectedThemes2D.value

        var themesToDelete = items.map({ item: Item ->
            if (item.theme == theme) return@map item
            else print("go die")
        })
        var themesToDelete2D = items2D.map({ item: Item2D ->
            if (item.theme == theme) return@map item
            else print("go die")
        })

        items.removeAll(themesToDelete)
        items2D.removeAll(themesToDelete2D)
        selectedThemes.onNext(items)
        selectedThemes2D.onNext(items2D)
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



fun Security.convertToItem(): Item2D {
    if (this.nom != null) {
        return Item2D(this.nom!!, Theme.security, this.polygon)
    }
    return Item2D("", Theme.security, this.polygon)
}
