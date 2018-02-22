package model

import java.util.*

/**
 * Created by genesis on 22/02/18.
 */
data class Security(val id:Int) {
    val nom:String? = null
    val theme:String? = null
    val soustheme:String? = null
    val identifiant:String? = null
    val idexterne:String? = null
    val siret:String? = null
    val datecreation:Date? = null
    val gid:Int = id
}