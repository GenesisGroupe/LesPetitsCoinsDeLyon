package com.genesis.lespetitscoinsdelyon.ihm.fragments

import android.app.Fragment
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.`interface`.IRecyclerViewInteraction
import com.genesis.lespetitscoinsdelyon.ihm.adapters.ThemesAdapter
import com.genesis.lespetitscoinsdelyon.viewmodel.MapViewModel
import com.genesis.lespetitscoinsdelyon.viewmodel.Theme
import kotlinx.android.synthetic.main.fragment_themes.*

/**
 * Created by hpatural on 22/02/2018.
 */
class ThemesFragment : Fragment(), IRecyclerViewInteraction {


    var themesList: ArrayList<Theme> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_themes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        this.themesList = MapViewModel.getInstance().availableThemes.value

                MapViewModel.getInstance().availableThemes.skip(1)
                .subscribe({ list ->
                    Log.d("RD", "list : " + list)
                    themesList = MapViewModel.getInstance().availableThemes.value
                    if (themesRecyclerView != null) {
                        themesRecyclerView.adapter.notifyDataSetChanged()
                    }
        })

        themesRecyclerView.adapter = ThemesAdapter(activity, themesList, this)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        themesRecyclerView.layoutManager = linearLayoutManager

        upArrow.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY)

    }

    override fun onItemSelected(theme: Theme) {
        MapViewModel.getInstance().selectTheme(theme)
    }

    override fun onItemUnselected(theme: Theme) {
        MapViewModel.getInstance().unseselectTheme()
    }
}