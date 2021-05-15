package com.trkkr.trkkrclean

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MapViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getInstance(this, getString(R.string.mapbox_access_token))

        setContentView(R.layout.activity_main)

        viewModel.createMapView(this)
        //val llMap = findViewById<LinearLayout>(R.id.LLMap)
        //llMap.addView()

        viewModel.setStyle(savedInstanceState)

    }
}