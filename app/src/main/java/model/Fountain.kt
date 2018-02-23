package model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by genesis on 22/02/18.
 */
data class Fountain(val id:Int) {

    var nom:String? = null
    var gestionnaire:String? = null
    var anneepose:Int? = null
    val gid:Int = id
    var the_geom:LatLng? = null

    constructor( id:Int,  nom:String, anneepose:Int, gestionnaire:String):this(id){
        this.nom = nom
        this.gestionnaire = gestionnaire
        this.anneepose = anneepose
    }
}