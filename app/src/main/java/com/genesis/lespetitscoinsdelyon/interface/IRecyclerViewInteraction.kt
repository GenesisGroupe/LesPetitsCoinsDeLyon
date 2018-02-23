package com.genesis.lespetitscoinsdelyon.`interface`

import com.genesis.lespetitscoinsdelyon.viewmodel.Theme

/**
 * Created by hpatural on 22/02/2018.
 */
interface IRecyclerViewInteraction {

    fun onItemSelected(theme: Theme)
    fun onItemUnselected(theme: Theme)
}