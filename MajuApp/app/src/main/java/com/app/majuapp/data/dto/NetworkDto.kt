package com.app.majuapp.data.dto

data class NetworkDto<T>(
    val `data`: T? = null,
    val message: String,
    val status: Int,
    val statusName: String?,
    val timestamp: Long,
    val error: String?
)