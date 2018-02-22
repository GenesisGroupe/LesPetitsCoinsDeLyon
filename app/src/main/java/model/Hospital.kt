package model

import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Created by Sylvain on 22/02/2018.
 */

data class Hospital(val id:Int) {
    val nom: String? = null
    val theme: String? = null
    val soustheme: String? = null
    val identifiant: String? = null
    val idexterne: String? = null
    val siret: String? = null
    val datecreation: Date? = null
    val gid: Int = id
    val the_geom: LatLng? = null

}