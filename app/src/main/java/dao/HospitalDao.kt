package dao

import android.content.Context
import com.genesis.lespetitscoinsdelyon.R
import com.google.android.gms.maps.model.LatLng
import model.Fountain
import model.Hospital
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by genesis on 22/02/18.
 */
class HospitalDao(ctx:Context) {
    val list:ArrayList<Hospital> = arrayListOf()

    init{
        val text = ctx.resources.openRawResource(R.raw.hopitaux)
                .bufferedReader().use { it.readText() }
        val jsonTxt = JSONObject(text)
        val jsonArray = jsonTxt.getJSONArray("values")

        val geoPoints = ctx.resources.openRawResource(R.raw.hopitauxgeo)
                .bufferedReader().use { it.readText() }
        val json = JSONObject(geoPoints)
        val jsonPoints = json.getJSONArray("values")

        for (i in 0 until jsonArray.length()){
            val jsonHospital = jsonArray.getJSONObject(i)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val myDate = simpleDateFormat.parse(jsonHospital.getString("datecreation"))

            var geo = jsonPoints.getJSONObject(i)
            val latLng = LatLng(geo.getDouble("lat"), geo.getDouble("lng"))
            var hospital = Hospital(
                    jsonHospital.getString("nom"),
                    jsonHospital.getString("theme"),
                    jsonHospital.getString("soustheme"),
                    jsonHospital.getString("identifiant"),
                    jsonHospital.getString("idexterne"),
                    jsonHospital.getString("siret"),
                    myDate,
                    jsonHospital.getInt("gid"),
                    latLng
            )

            list.add(hospital)
        }
    }

}