package com.app.majuapp.screen.culture

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.repository.LocationTracker
import com.app.majuapp.domain.usecase.CultureUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CultureViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val cultureUsecase: CultureUsecase,
): ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>( null )
    val currentLocation: StateFlow<Location?> = _currentLocation

    val cultureEventList = cultureUsecase.cultureEventList

    init {
//        getCurrentLocation()
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


}