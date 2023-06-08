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
         fun getAllCities(context: Context):ArrayList<City> {
            return try {
                val citiesFile: InputStream = context.assets.open("cities/cities.json")
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
            return  getAllCities(context)
                .stream()
                .filter{city-> country == city.country }
                .map{city-> CityTypes(city.name,city.country)}
                .distinct()
                .collect(Collectors.toCollection { ArrayList() })

        }
        fun getAllCountries(context: Context):List<String>{
            return getAllCities(context)
                .stream()
                .filter{city-> city.country.isNotEmpty()}
                .map{city-> city.country}
                .distinct()
                .sorted()
                .collect(Collectors.toList())
        }
    }



}