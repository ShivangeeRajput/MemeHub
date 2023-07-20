package com.example.retrofit_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://meme-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //connecting retrofit to API interface
    val apiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}