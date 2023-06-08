package com.example.islamapplictation.ui.prayertimes.prayertimeshome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.cities.City
import com.example.islamapplictation.data.pojo.cities.CityTypes

class CitiesAdapter(context: Context, private val cityArrayList: ArrayList<CityTypes>) :
    ArrayAdapter<CityTypes>(context,0,cityArrayList) {
    override fun getItem(position: Int): CityTypes? {
        return cityArrayList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }
    private fun initView(position: Int, _convertView: View?, parent: ViewGroup):View {
        var convertView:View? = _convertView
        return if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_spinner, parent, false)
            val cityTextView =convertView.findViewById(R.id.spCity)as TextView
            cityTextView.text = cityArrayList[position].city
            val countryTextView = convertView.findViewById(R.id.spCountry)as TextView
            countryTextView.text = cityArrayList[position].country
            convertView
        } else {
            convertView
        }
    }

}