package com.example.weatherapp.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.city.CityDataModel
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter(private val function: (CityDataModel) -> Unit) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    var mList: ArrayList<CityDataModel> = ArrayList()

    fun setCities(list: List<CityDataModel>?) {
        if (list != null) {
            mList.clear()
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ViewHolder(view, function)
    }


    class ViewHolder(itemView: View, val function: (CityDataModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: CityDataModel) {
            itemView.item_city_country_txt.text = city.name
            itemView.item_city_city_txt.text = city.capital

            itemView.setOnClickListener {
                function(city)
            }
        }
    }
}