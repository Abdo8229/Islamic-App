package com.example.islamapplictation.ui.quran.soraSearchList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoraListViewModel  @Inject constructor(
    private val getAllSoraUseCase:GetAllSoraUseCase
    ) : ViewModel() {
    private val TAG = "SoraListViewModel"
//    private val soraListRepoImp: SoraListRepoImp by lazy {
//        SoraListRepoImp()
//    }


    fun getDataByEvent(soraListEvent: SoraListEvent)
    {
        when(soraListEvent){
            SoraListEvent.GetAllSoraEvent->getAllSoras()
        }
    }

    private val _soraListStatusMutableStateFlow = MutableStateFlow<SoraListStatus>(SoraListStatus.Empty)
    val soraListStatusStateFlow: StateFlow<SoraListStatus> = _soraListStatusMutableStateFlow
    private fun getAllSoras() {
        viewModelScope.launch(Dispatchers.IO) {
            _soraListStatusMutableStateFlow.value= getAllSoraUseCase(NoParam)



        }
    }


}


