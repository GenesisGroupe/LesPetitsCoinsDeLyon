package com.genesis.lespetitscoinsdelyon.ihm.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.extensions.inflate
import com.genesis.lespetitscoinsdelyon.viewmodel.Theme
import com.genesis.lespetitscoinsdelyon.`interface`.IRecyclerViewInteraction


/**
 * Created by hpatural on 22/02/2018.
 */
class ThemesAdapter(val context: Context, val themes: List<Theme>, val listener: IRecyclerViewInteraction) : RecyclerView.Adapter<ThemesAdapter.ThemesHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ThemesHolder{
        val inflatedView = parent!!.inflate(R.layout.item_theme, false)
        return ThemesHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return themes.size
    }

    override fun onBindViewHolder(holder: ThemesHolder?, position: Int) {
        var theme = themes.get(position)
        if (holder != null) {
            holder.themeName.text = theme.name

            if (theme.selected) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                holder.themeName.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                holder.themeName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }

            holder.itemView.setOnClickListener({
                theme.selected = !theme.selected

                if (theme.selected) {
                    listener.onItemSelected(theme)
                } else {
                    listener.onItemUnselected(theme)
                }

                notifyItemChanged(position)
            })
        }
    }


    class ThemesHolder(row: View?): RecyclerView.ViewHolder(row) {
        val themeName: TextView

        init {
            this.themeName = row?.findViewById(R.id.themeName)!!
        }

    }
}