package com.example.alkomatonline.Remote.Common

import com.example.alkomatonline.Remote.IGoogleAPIService
import com.example.alkomatonline.Remote.RetrofitClient


object Common {

    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleAPIService:IGoogleAPIService
        get()= RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}