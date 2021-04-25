package com.example.repoviewer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance{
    private var retrofit : Retrofit? = null
    private val BASE_URL = "https://api.github.com/"

    fun getRetrofitInstance() : Retrofit{
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}