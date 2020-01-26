package com.tno.cuhackit2020.net

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tno.cuhackit2020.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {
    val service by lazy {
        Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://8132095d.ngrok.io")
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(ChuckerInterceptor(App.app))
                    .build()
            )
            .build()
            .create(IssueService::class.java)
    }
}