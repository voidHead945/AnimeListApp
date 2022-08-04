package com.android.animeAndroidApp.models

import java.lang.reflect.Array

data class Pictures(
    val request_hash: String,
    val request_cached: Boolean,
    val request_cache_expiry: Int,
    val API_DEPRECATION: Boolean,
    val API_DEPRECATION_DATE: String,
    val API_DEPRECATION_INFO: String,
    val pictures: List<Map<String, String>>
)
