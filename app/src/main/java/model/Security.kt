package model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by genesis on 22/02/18.
 */
//"fields":[
data class Security(val id:Int) {
    var nom:String? = null
    var theme:String? = null
    var soustheme:String? = null
    var identifiant:String? = null
    var idexterne:String? = null
    var siret:String? = null
    var datecreation:Date? = null
    var gid:Int = id
    var polygon:ArrayList<LatLng>? = null

    constructor(nom:String, theme:String, soustheme:String, identifiant:String,idexterne:String, siret:String, datecreation:Date,id:Int,polygon: ArrayList<LatLng>):this(id){
        this.nom = nom
        this.theme = theme
        this.soustheme = soustheme
        this.identifiant = identifiant
        this.idexterne = idexterne
        this.siret = siret
        this.polygon = polygon
        this.datecreation = datecreation
    }

}