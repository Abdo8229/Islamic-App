package com.example.islamapplictation.data.citiesprovider

import android.content.Context
import com.example.islamapplictation.data.pojo.cities.City
import com.example.islamapplictation.data.pojo.cities.CityTypes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.IOException
import org.jetbrains.annotations.NotNull
import java.io.InputStream
import java.util.stream.Collectors

class CitiesProvider {
    companion object {
         private fun getAllCities(context: Context, country: String):ArrayList<City> {
            return try {
                val citiesFile: InputStream = context.assets.open("cities/${country}.json")
                val size: Int = citiesFile.available()
                val bytes = ByteArray(size)
                citiesFile.read(bytes)
                citiesFile.close()
                val citiesString = String(bytes, Charsets.UTF_8)
                val gson = Gson()
                val itemType = object : TypeToken<ArrayList<City>>() {}.type
                gson.fromJson(citiesString, itemType)
            } catch (e: IOException) {
                ArrayList()
            }

        }
        fun getCityByCountry(context: Context,@NotNull country:String):ArrayList<CityTypes>{
            if (country.isEmpty()) return ArrayList()
            return  getAllCities(context,country)
                .stream()
                .map{city-> CityTypes(city.city,city.country)}
                .distinct()
                .collect(Collectors.toCollection { ArrayList() })

        }
    }



}