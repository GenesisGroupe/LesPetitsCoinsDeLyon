package com.genesis.lespetitscoinsdelyon.ihm.fragments

import android.app.Fragment
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genesis.lespetitscoinsdelyon.R
import com.genesis.lespetitscoinsdelyon.ihm.adapters.ThemesAdapter
import kotlinx.android.synthetic.main.fragment_themes.*

/**
 * Created by hpatural on 22/02/2018.
 */
class ThemesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_themes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //optionsSwipeLayout.addDrag(SwipeLayout.DragEdge.Top, (activity as MapsActivity).map.view)

        val themes = ArrayList<String>()
        themes.add("Hôpitaux")
        themes.add("Toilettes")
        themesRecyclerView.adapter = ThemesAdapter(activity, themes)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        themesRecyclerView.layoutManager = linearLayoutManager

        upArrow.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY)

    }

}