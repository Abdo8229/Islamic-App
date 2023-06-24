package com.example.islamapplictation.data.citiesprovider

import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CitiesProviderTest  {

    @Test
    fun `empty country return empty`() {
        val cities = CitiesProvider.getCityByCountry(ApplicationProvider.getApplicationContext(),"")
           Truth.assertThat(cities).isEmpty()
    }
    @Test
    fun `cairo country return cairo`() {
        val cities = CitiesProvider.getCityByCountry(ApplicationProvider.getApplicationContext(),"eg")
      Truth.assertThat(cities[0].city).isEqualTo("Cairo")
    }
}