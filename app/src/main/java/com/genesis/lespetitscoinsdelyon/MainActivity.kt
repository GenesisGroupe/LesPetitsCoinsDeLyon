package com.genesis.lespetitscoinsdelyon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dao.FountainDao
import dao.HospitalDao
import dao.SecurityDao

/**
 * Created by hpatural on 22/02/2018.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        val securityDao = SecurityDao(this)
        val list = securityDao.list
        for( i in 0 until list.size){
            Log.d("Test", ""+list[i].nom);
        }

    }
}