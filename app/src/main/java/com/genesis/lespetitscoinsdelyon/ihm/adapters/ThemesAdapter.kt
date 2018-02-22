package com.genesis.lespetitscoinsdelyon.ihm.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.extensions.inflate
import com.genesis.lespetitscoinsdelyon.viewmodel.Theme


/**
 * Created by hpatural on 22/02/2018.
 */
class ThemesAdapter(val context: Context, val themes: List<Theme>) : RecyclerView.Adapter<ThemesAdapter.ThemesHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ThemesHolder{
        val inflatedView = parent!!.inflate(R.layout.item_theme, false)
        return ThemesHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return themes.size
    }

    override fun onBindViewHolder(holder: ThemesHolder?, position: Int) {
        val theme = themes.get(position)
        if (holder != null) {
            holder.themeName.text = theme.name
        }
    }

    class ThemesHolder(row: View?): RecyclerView.ViewHolder(row) {
        val themeName: TextView

        init {
            this.themeName = row?.findViewById(R.id.themeName)!!
        }
    }
}