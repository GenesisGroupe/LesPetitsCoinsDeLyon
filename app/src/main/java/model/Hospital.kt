package model

import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Created by Sylvain on 22/02/2018.
 */

data class Hospital(val id:Int) {
    var nom: String? = null
    var theme: String? = null
    var soustheme: String? = null
    var identifiant: String? = null
    var idexterne: String? = null
    var siret: String? = null
    var datecreation: Date? = null
    var gid: Int = id
    var the_geom: LatLng? = null

    constructor(nom:String, theme:String, soustheme:String, identifiant:String,idexterne:String, siret:String, datecreation:Date,id:Int,latLng:LatLng):this(id){
        this.nom = nom
        this.theme = theme
        this.soustheme = soustheme
        this.identifiant = identifiant
        this.idexterne = idexterne
        this.siret = siret
        this.the_geom = latLng
        this.datecreation = datecreation
    }

}