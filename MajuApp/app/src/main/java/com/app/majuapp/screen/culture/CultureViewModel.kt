package com.app.majuapp.screen.culture

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.LocationTracker
import com.app.majuapp.domain.usecase.CultureUsecase
import com.app.majuapp.util.Constants.GENRES
import com.app.majuapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CultureViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val cultureUsecase: CultureUsecase,
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    val cultureEventList: StateFlow<List<CultureEventDomainModel>> = cultureUsecase.cultureEventList
    val cultureEventListNetworkResult = cultureUsecase.cultureEventListNetworkResult
    val cultureEventToggleNetworkResult: StateFlow<NetworkResult<NetworkDto<Boolean>>> = cultureUsecase.cultureEventToggleNetworkResult

    private val _genreChoicedIdx = MutableStateFlow<Int>(-1)
    val genreChoicedIdx: StateFlow<Int> = _genreChoicedIdx

    private val _focusedEvent = MutableStateFlow<Int>(-1)
    val focusedEvent: StateFlow<Int> = _focusedEvent

    init {
        getAllCultureEvents()
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = locationTracker.getCurrentLocation()
            result?.let {
                _currentLocation.emit(result)
            }
        }
    }

    fun getAllCultureEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            cultureUsecase.getAllCultureEvents()
        }
    }

    fun genreChoice(choicedIdx: Int) {
        _genreChoicedIdx.value = if (choicedIdx == _genreChoicedIdx.value) -1 else choicedIdx
        viewModelScope.launch(Dispatchers.IO) {
            if (_genreChoicedIdx.value == -1)
                cultureUsecase.getAllCultureEvents()
            else
                cultureUsecase.getGenreCultureEvents(GENRES[choicedIdx])
        }
    }

    fun toggleCultureLike(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                cultureUsecase.toggleCultureLike(eventId)
        }
    }

    fun focusEvent(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _focusedEvent.emit(eventId)
        }
    }

    fun unfocusEvent() = viewModelScope.launch(Dispatchers.IO) {
        _focusedEvent.emit(-1)
    }

}