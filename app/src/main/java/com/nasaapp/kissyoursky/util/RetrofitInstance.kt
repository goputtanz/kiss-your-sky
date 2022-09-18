package com.nasaapp.kissyoursky.util

import com.nasaapp.kissyoursky.home.data.AstronomyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitInstance {

    companion object {

        private val retrofitAstronomy by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_Astronomy)
                .client(okHttpClient)
                .build()
        }


        val astronomyService by lazy {
            retrofitAstronomy.create(AstronomyService::class.java)
        }

    }
}