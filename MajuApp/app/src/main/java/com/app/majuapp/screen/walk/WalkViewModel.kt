package com.app.majuapp.screen.walk

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.model.walk.CoordinateData
import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import com.app.majuapp.domain.repository.walk.WalkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WalkViewModel_창영"

@HiltViewModel
class WalkViewModel @Inject constructor(
    private val walkRepository: WalkRepository
) : ViewModel() {

    // ====================================== Permission ======================================
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }


    // InformDialog
    private val _showInformDialog = MutableStateFlow<Boolean>(false)
    val showInfromDialog = _showInformDialog.asStateFlow()

    fun setShowInfromDialog() {
        _showInformDialog.value = !_showInformDialog.value
    } // End of setShowInfromDialog()

    // NowLocation
    private val _nowLocation = MutableStateFlow<CoordinateData?>(null)
    val nowLocation = _nowLocation.asStateFlow()

    fun setNowLocation() {

    }



    // ========================================= getWalkingTrails ======================================
    // 현재 위치 좌표를 기반으로 해당 값이 변경될 경우, 이 값을 기반으로 가까운 산책로를 찾는다.
    val currentCoordinateData: MutableStateFlow<CoordinateData> = MutableStateFlow(
        CoordinateData(37.568133, 126.968707)
    )

    val data = mutableStateOf<WalkingTrailResultData?>(null)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)

    fun setCurrentCoordinate(coordinateData: CoordinateData) {
        currentCoordinateData.value = coordinateData
    } // End of setCurrentCoordinate()

    fun getWalkingTrails(coordinateData: CoordinateData) {
        isLoading.value = true
        // 산책로 가져오는 데이터 호출
        viewModelScope.launch {
            isLoading.value = false

            walkRepository.getWalkingTrails(coordinateData).catch {
                error.value = it.message.toString()
            }.collect {
                if (it.isSuccessful && it.body() != null) {
                    data.value = it.body()
                }
            }
        }
    } // End of getWalkingTrails()


    val result = walkRepository.getWalkingTrails2(currentCoordinateData.value)

    fun test() {
        if (currentCoordinateData.value != null) {
            val result = walkRepository.getWalkingTrails2(currentCoordinateData.value)
        }
    }


//    val walkingTrailList: Flow<Response<WalkingTrailResultData>> =
//        currentCoordinateData.flatMapLatest { coordinate ->
//            Log.d(TAG, "coordinate : $coordinate ")
//
//            if (coordinate != null) {
//                walkRepository.getWalkingTrails(currentCoordinateData.value!!).map { response ->
//                    if (response.isSuccessful) {
//                        // 성공적인 응답 처리
//                        response
//                    } else {
//                        // 실패한 응답 처리
//                        response
//                    }
//                }
//            } else {
//                // currentCoordinate가 null인 경우 빈 Flow 반환
//                flowOf()
//            }
//        }


} // End of WalkViewModel
