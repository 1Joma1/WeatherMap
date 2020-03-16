package com.example.weatherapp.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    private fun getWeather(latLng: LatLng, city: Boolean) {
        viewModel.getWeatherData("metric", latLng.latitude, latLng.longitude)
            .observe(this@MapFragment, Observer {
                if (city) {
                    addMarkerToMap(latLng, it.name)
                } else {
                    city_name_txt?.text = it.name
                    deg_txt?.text = "${it!!.main.temp.toInt()}°"
                    info_txt?.text = it.weather[0].main
                    Glide.with(this.requireContext())
                        .load("http://openweathermap.org/img/wn/${it.weather[0].icon}.png")
                        .into(ic_weather_small)
                    humidity_txt?.text = "${it.main.humidity}% влажности"
                }
            })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener {
            getWeather(it, true)
            addMarkerToMap(it, "loading")
        }
        googleMap.setOnInfoWindowClickListener {
            val weatherFragment = WeatherFragment()
            val fm = activity?.supportFragmentManager
            if (fm?.findFragmentByTag("weatherF") != null) {
                fm.beginTransaction().show(fm.findFragmentByTag("weatherF")!!).commit()
            } else {
                fm!!.beginTransaction().add(R.id.mainFragment, weatherFragment, "weatherF").commit()
            }
            getWeather(it.position, false)
        }
    }

    private fun addMarkerToMap(location: LatLng, city: String) {
        googleMap.clear()
        val markerOptions = MarkerOptions()
            .position(location)
            .title(city)
            .icon(getMarker())
        googleMap.addMarker(markerOptions).showInfoWindow()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gmap.onCreate(savedInstanceState)
        gmap.onResume()
        gmap.getMapAsync(this)
    }

    //SVG converter ;)
    private fun getMarker(): BitmapDescriptor? {
        return ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_map_marker)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}
