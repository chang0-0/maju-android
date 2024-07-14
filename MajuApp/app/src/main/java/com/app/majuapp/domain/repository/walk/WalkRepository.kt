package com.app.majuapp.domain.repository.walk

import android.util.Log
import com.app.majuapp.domain.api.WalkApi
import com.app.majuapp.domain.model.walk.CoordinateData
import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import com.app.majuapp.screen.walk.RequestState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "WalkRepository_창영"
class WalkRepository @Inject constructor(
    private val walkApi: WalkApi
) {
    // ========================================= getWalkingTrails ======================================
//    suspend fun getWalkingTrails(coor: CoordinateData) = flow {
//        emit(NetworkResult.Loading())
//        val result = walkApi.getWalkingTrails(coor.lat!!, coor.lng!!)
//        emit(NetworkResult.Loading())
//        if (result.isSuccessful && result.body() != null) {
//            emit(NetworkResult.Success(result.body()))
//        } else {
//            emit(NetworkResult.Error(result.code(), result.errorBody().toString(), null))
//        }
//    }.catch {
//        emit(NetworkResult.Error(0, it.message.toString(), null))
//    }.flowOn(Dispatchers.IO)

    suspend fun getWalkingTrails(coor: CoordinateData): Flow<Response<WalkingTrailResultData>> {
        val ret = walkApi.getWalkingTrails(coor.lat!!, coor.lng!!)
        return flow { emit(ret) }.flowOn(Dispatchers.IO)
    } // End of getWalkingTrails()

    fun getWalkingTrails2(coor: LatLng): Flow<RequestState<WalkingTrailResultData?>> {
        return flow {
            emit(RequestState.Loading)
            val ret = walkApi.getWalkingTrails(coor.latitude, coor.longitude)
            emit(RequestState.Success(data = ret.body()))
        }
    } // End of getWalkingTrails()

} // End of WalkRepository class