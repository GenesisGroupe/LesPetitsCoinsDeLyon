package dao

import android.content.Context
import com.genesis.lespetitscoinsdelyon.R
import com.google.android.gms.maps.model.LatLng
import model.Hospital
import model.Security
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.ArrayList

/**
 * Created by genesis on 22/02/18.
 */
class SecurityDao(ctx:Context) {

    val list: ArrayList<Security> = arrayListOf()

    init{
        val text = ctx.resources.openRawResource(R.raw.securities)
                .bufferedReader().use { it.readText() }
        val jsonTxt = JSONObject(text)
        val jsonArray = jsonTxt.getJSONArray("values")

        val geoPoints = ctx.resources.openRawResource(R.raw.securitiesgeo)
                .bufferedReader().use { it.readText() }
        val json = JSONObject(geoPoints)
        val jsonPolygons= json.getJSONArray("values")

        for (i in 0 until jsonArray.length()){
            val jsonSecurity = jsonArray.getJSONObject(i)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val myDate = simpleDateFormat.parse(jsonSecurity.getString("datecreation"))

            var geo = jsonPolygons.getJSONArray(i)
            var polygon = ArrayList<LatLng>()
            for (j in 0 until geo.length()) {
                val latLng = LatLng(geo.getJSONObject(j).getDouble("lat"), geo.getJSONObject(j).getDouble("lng"))
                polygon.add(latLng)
            }
            var security = Security(
                    jsonSecurity.getString("nom"),
                    jsonSecurity.getString("theme"),
                    jsonSecurity.getString("soustheme"),
                    jsonSecurity.getString("identifiant"),
                    jsonSecurity.getString("idexterne"),
                    jsonSecurity.getString("siret"),
                    myDate,
                    jsonSecurity.getInt("gid"),
                    polygon
            )

            list.add(security)
        }
    }
}