package com.example.weatherapp.ui.detail_city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.activity_detail_city.*


class DetailCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_city)

        val cityFlag = intent.getStringExtra("city")

        city_flags.loadData("<img style=\"width:100%\" src=\"$cityFlag\"/>", "text/html", "UTF-8")
    }

}