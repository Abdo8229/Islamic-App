package com.example.islamapplictation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islamapplictation.data.pojo.quranvoice.FilterdQuranVoice
import com.example.islamapplictation.data.pojo.quranvoice.QuranVoice
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranFilteredVoiceUseCase
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranVoiceApiService
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranVoiceFilterdStatus
import com.example.islamapplictation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val quranFilteredVoiceUseCase: QuranFilteredVoiceUseCase) :
    ViewModel() {

 private var _quranVoceMutableStateFlow  =     MutableStateFlow<List<FilterdQuranVoice>>  (emptyList<FilterdQuranVoice>())
        val quranFilterdStateFlow: StateFlow<List<FilterdQuranVoice>> = _quranVoceMutableStateFlow
    suspend fun getFilteredQuranVoices() {
        viewModelScope.launch (Dispatchers.IO){
            when (val useCase = quranFilteredVoiceUseCase.invoke()) {
                is QuranVoiceFilterdStatus.Success -> {
                _quranVoceMutableStateFlow.value = useCase.resultArrayList
                }

                is QuranVoiceFilterdStatus.Failure -> {
                        _quranVoceMutableStateFlow.value = arrayListOf(FilterdQuranVoice("",useCase.errorText))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}


