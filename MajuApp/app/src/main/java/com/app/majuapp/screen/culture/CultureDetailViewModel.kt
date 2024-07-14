package com.app.majuapp.screen.culture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.usecase.CultureUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CultureDetailViewModel @Inject constructor(
    private val cultureUsecase: CultureUsecase
): ViewModel() {

    val cultureEventDetailNetworkResult = cultureUsecase.cultureEventDetailNetworkResult

    fun getCultureEventDetail(cultureEventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cultureUsecase.getCultureEventsDetail(cultureEventId)
        }
    }

    fun toggleCultureLike(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cultureUsecase.toggleCultureLike(eventId)
        }
    }

}