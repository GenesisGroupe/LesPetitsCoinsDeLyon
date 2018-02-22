package model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Sylvain on 22/02/2018.
 */

data class Hospital{
    val nom: String
    val theme: String

    constructor(nom: String, theme: String) {
        this.nom = nom
        this.theme = theme
    }

}