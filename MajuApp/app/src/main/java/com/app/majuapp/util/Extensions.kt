package com.app.majuapp.util

import android.util.Log
import com.app.majuapp.data.repositoryImp.TAG
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

suspend fun <T> MutableStateFlow<NetworkResult<T>>.setNetworkResult(response: Response<T>) {
    this.emit(NetworkResult.Loading())
    CoroutineScope(Dispatchers.IO).launch {
        try {
            when {
                response.isSuccessful -> {
                    this@setNetworkResult.emit(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    )
                }
                response.errorBody() != null -> this@setNetworkResult.emit(
                    NetworkResult.Error(
                        response.code(),
                        response.errorBody()?.string(),
                    )
                )
                else -> this@setNetworkResult.emit(
                    NetworkResult.Error(
                        response.code(),
                        "Something is Wrong....",
                    )
                )
            }
        } catch (e: java.lang.Exception) {
            this@setNetworkResult.emit(
                NetworkResult.Error(
                    999,
                    e.message,
                    e
                )
            )
            Log.e(TAG, "login repo err ${e.message}")
        } // End of try-catch
    }
} // End of setNetworkResult