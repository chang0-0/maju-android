package com.app.majuapp.screen.culture

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.repository.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CultureViewModel @Inject constructor(
    private val locationTracker: LocationTracker
): ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>( null )
    val currentLocation: StateFlow<Location?> = _currentLocation

    init {
        getCurrentLocation()
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            val result = locationTracker.getCurrentLocation()
            result?.let {
                _currentLocation.emit(result)
            }
        }
    }

}