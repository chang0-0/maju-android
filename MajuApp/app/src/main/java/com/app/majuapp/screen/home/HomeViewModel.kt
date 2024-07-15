package com.app.majuapp.screen.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.LocationTracker
import com.app.majuapp.domain.usecase.HomeCultureUsecase
import com.app.majuapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val homeCultureUsecase: HomeCultureUsecase
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()

    val cultureHomeRecommendation: StateFlow<CultureEventDomainModel?> = homeCultureUsecase.cultureHomeRecommendation
    val cultureHomeRecommendationNetworkResult: StateFlow<NetworkResult<NetworkDto<CultureEventDomainModel?>>> = homeCultureUsecase.cultureHomeRecommendationNetworkResult

    init {
        getCurrentLocation()
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = locationTracker.getCurrentLocation()
            result?.let {
                _currentLocation.emit(result)
            }
        }
    }

    fun getCultureHomeRecommendation(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeCultureUsecase.getCultureHomeRecommendation(lat, lon)
        }
    }

}