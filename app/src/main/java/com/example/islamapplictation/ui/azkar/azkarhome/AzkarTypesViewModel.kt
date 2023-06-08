package com.example.islamapplictation.ui.azkar.azkarhome

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.islamapplictation.data.azkarprovider.AzkarProvider
import com.example.islamapplictation.data.pojo.ZekrTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AzkarTypesViewModel @Inject constructor(
    @Named("proviedsAzkarTypes")
    val proviedsAzkarTypes: HashSet<ZekrTypes>
) : ViewModel() {

    fun getAzkarType(): HashSet<ZekrTypes> {
        return proviedsAzkarTypes
    }
}