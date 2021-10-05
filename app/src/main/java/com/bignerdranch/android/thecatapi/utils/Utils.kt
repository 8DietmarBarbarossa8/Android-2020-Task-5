package com.bignerdranch.android.thecatapi.utils

object Utils {
    private const val LIMIT = 10
    private const val API_KEY = "2de238b9-a088-43b0-8b22-f7625d821b36"
    const val QUERY_KEY = "/v1/images/search?limit=$LIMIT&order=ACSapi_key=$API_KEY"
    const val CAT_API_SITE_KEY = "https://api.thecatapi.com"
    const val IMAGE_KEY = "CONCRETE_CAT_IMAGE_FULL"

    // influence how much cats portion need to add
    var countOfPortions = 0
    // add possibility to show message notification before exiting
    var isMayExit = false
}