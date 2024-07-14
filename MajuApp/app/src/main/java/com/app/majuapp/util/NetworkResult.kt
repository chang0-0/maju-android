package com.app.majuapp.util

sealed class NetworkResult<T>(var data: Any? = null, val message: String? = "") {
    data class Success<T> constructor(val value: T) : NetworkResult<T>(value)
    class Error<T> @JvmOverloads constructor(
        var code: Int? = null,
        var msg: String? = null,
        var exception: Throwable? = null
    ) : NetworkResult<T>(code, msg)
    class Loading<T> : NetworkResult<T>()
    class Idle<T> : NetworkResult<T>()
} // End of NetworkResult class

