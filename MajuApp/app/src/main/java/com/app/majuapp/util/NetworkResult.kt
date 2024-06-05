package com.app.majuapp.util

sealed class NetworkResult<T>(var data: Any? = null, val message: String? = "") {
    data class Success<T> constructor(val value: T) : NetworkResult<T>(value)
    class Error<T> @JvmOverloads constructor(
        var code: Int? = null,
        var msg: String? = null,
        var exception: Throwable? = null

    ) : NetworkResult<T>(code, msg)

    class Loading<T> : NetworkResult<T>()
} // End of NetworkResult class

sealed class ResourceState<T> {
    object Uninitialized : ResourceState<Nothing>()
    object Empty : ResourceState<Nothing>()
    // object Loading : ResourceState<Nothing>()

    class Loading<T> : ResourceState<T>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error<Any>(val error: Any) : ResourceState<Any>()
} // End of ResourceState sealed class