package com.app.majuapp.util

object Constants {
    const val BASE_URL = "http://omaju.duckdns.org:8000"
    const val API_BASE_URL = "${BASE_URL}/api/v1/"

    val GENRE_MUSIC = "음악"
    val GENRE_EXHIBITION = "전시"
    val GENRE_EXPERIENCE = "체험"
    val GENRE_THEATER = "연극"
    val GENRES = listOf(GENRE_MUSIC, GENRE_EXHIBITION, GENRE_EXPERIENCE, GENRE_THEATER)

}