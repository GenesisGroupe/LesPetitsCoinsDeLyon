package dao

import android.content.Context
import com.genesis.lespetitscoinsdelyon.R
import com.google.android.gms.maps.model.LatLng
import model.Fountain
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by genesis on 22/02/18.
 */
class FountainDao(ctx:Context) {

    val list:ArrayList<Fountain> = arrayListOf()

    init{
        val text = ctx.resources.openRawResource(R.raw.fountaines)
                .bufferedReader().use { it.readText() }
        val jsonTxt = JSONObject(text)
        val jsonArray = jsonTxt.getJSONArray("values")

        val geoPoints = ctx.resources.openRawResource(R.raw.fountainsgeo)
                .bufferedReader().use { it.readText() }
        val json = JSONObject(geoPoints)
        val jsonPoints = json.getJSONArray("values")

        for (i in 0 until jsonArray.length()){
            val jsonFountain = jsonArray.getJSONObject(i)
            var anneepose = 0
            try {
                 anneepose = jsonFountain.getInt("anneepose")
            }catch ( e: JSONException){
                anneepose = 0
            }
            var fountain = Fountain(jsonFountain.getInt("gid"),
                    jsonFountain.getString("nom"),
                    anneepose,
                    jsonFountain.getString("gestionnaire"))
            var geo = jsonPoints.getJSONObject(i)
            fountain.the_geom = LatLng(geo.getDouble("lat"), geo.getDouble("lng"))

            list.add(fountain)
        }
    }

}