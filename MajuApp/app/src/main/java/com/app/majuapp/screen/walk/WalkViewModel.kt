package com.app.majuapp.screen.walk

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.model.walk.WalkTrailData
import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import com.app.majuapp.domain.repository.walk.WalkRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WalkViewModel_창영"

@HiltViewModel
class WalkViewModel @Inject constructor(
    private val walkRepository: WalkRepository
) : ViewModel() {

    // ========================================= Information Dialog ======================================
    private val _showInformDialog = MutableStateFlow<Boolean>(false)
    val showInformDialog = _showInformDialog.asStateFlow()

    fun setShowInfromDialog() {
        _showInformDialog.value = !_showInformDialog.value
    } // End of setShowInfromDialog()

    // ========================================= 사용자의 현재위치 ======================================
    // ========================================= NowLocation ======================================
    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun setCurrentLocation(coordinate: LatLng) {
        _currentLocation.value = coordinate
    } // End of setCurrentLocation()


    // ========================================= 현재 사용자의 선택한 산책로 데이터 ======================================
    private val _currentChooseWalkingTrail = MutableStateFlow<WalkTrailData?>(null)
    val currentChooseWalkingTrail = _currentChooseWalkingTrail.asStateFlow()

    fun setCurrentChooseWalkingTrail(newData: WalkTrailData) {
        _currentChooseWalkingTrail.value = newData
    } // End of setCurrentChooseWalkingTrail()


    // ========================================= getWalkingTrails ======================================
    // 현재 위치 좌표를 기반으로 해당 값이 변경될 경우, 이 값을 기반으로 가까운 산책로를 찾는다.

//    val data = mutableStateOf<WalkingTrailResultData?>(null)
//    val isLoading: MutableState<Boolean> = mutableStateOf(false)
//    val error: MutableState<String?> = mutableStateOf(null)

    private val _walkingTrailData =
        MutableStateFlow<RequestState<WalkingTrailResultData?>>(RequestState.Idle)
    val walkingTrailData = _walkingTrailData.asStateFlow()


    fun getWalkingTrails() {
        viewModelScope.launch {
            _currentLocation.collectLatest { location ->
                if (location != null) {
                    walkRepository.getWalkingTrails2(location).collect { result ->
                        _walkingTrailData.value = result
                    }
                } else {
                    _walkingTrailData.value = RequestState.Idle
                }
            }
        }
    }

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissPermissionDialog() {
        visiblePermissionDialogQueue.removeFirst()
    } // End of dismissPermissionDialog()

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    } // End of onPermissionResult()
} // End of WalkViewModel
