package model

import com.beust.klaxon.Json
import com.google.android.gms.maps.model.LatLng

/**
 * Created by genesis on 22/02/18.
 */
data class Fountain(val id:Int) {

    @Json(name = "nom")
    var nom:String? = null
    @Json(name = "gestionnaire")
    var gestionnaire:String? = null
    @Json(name = "anneepose")
    var anneepose:Int? = null
    @Json(name = "gid")
    val gid:Int = id
    @Json(name = "the_geom")
    var the_geom:LatLng? = null

    constructor( id:Int,  nom:String, anneepose:Int, gestionnaire:String):this(id){
        this.nom = nom
        this.gestionnaire = gestionnaire
        this.anneepose = anneepose
    }
}