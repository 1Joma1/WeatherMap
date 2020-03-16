package com.example.weatherapp.ui.city

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.model.city.CityDataModel
import com.example.weatherapp.ui.detail_city.DetailCityActivity
import kotlinx.android.synthetic.main.fragment_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment : BaseFragment(R.layout.fragment_city) {

    private lateinit var searchEditText: EditText
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchRecyclerViewAdapter: CityAdapter
    private val viewModel: CityViewModel by viewModel()

    override fun initViews(view: View) {
        bindView(view)
        searchCity()
    }

    override fun loadingStatus() {

    }

    private fun bindView(view: View) {
        searchEditText = view.findViewById(R.id.search_edit_text)
        searchRecyclerView = view.findViewById(R.id.city_recycler)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerViewAdapter = CityAdapter(this@CityFragment::onClickItem)
        searchRecyclerView.adapter = searchRecyclerViewAdapter
    }

    private fun onClickItem(city: CityDataModel) {
        val intent = Intent(context, DetailCityActivity::class.java)
        intent.putExtra("city", city.flag)
        startActivity(intent)
    }


    private fun searchCity() {
        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (searchEditText.text.toString().length > 2) {
                    viewModel.getCityData(search_edit_text.text.toString().trim())
                        .observe(activity!!, Observer {
                            Log.e("--------", "$it")
                            searchRecyclerViewAdapter.setCities(it)
                        })
                }
            }
        })
    }

}
