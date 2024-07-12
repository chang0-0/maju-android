package com.app.majuapp.util

object Constants {
    const val BASE_URL = "http://omaju.duckdns.org:8000"
    const val API_BASE_URL = "${BASE_URL}/api/v1/"

    val CATEGORY_MUSIC = "음악"
    val CATEGORY_EXHIBITION = "전시"
    val CATEGORY_EXPERIENCE = "체험"
    val CATEGORY_THEATER = "연극"
    val CATEGORIES = listOf(CATEGORY_MUSIC, CATEGORY_EXHIBITION, CATEGORY_EXPERIENCE, CATEGORY_THEATER)

}