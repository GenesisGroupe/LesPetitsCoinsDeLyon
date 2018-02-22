package com.genesis.lespetitscoinsdelyon

import android.app.Application
import android.content.Context

/**
 * Created by Sylvain on 22/02/2018.
 */

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }

}

val Context.myApp: MyApplication
    get() = applicationContext as MyApplication