package com.mysoft.uldbsmarket.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    companion object {
        public const val IMAGE_DOWNLOAD_URL = "http://192.168.0.83:8081/uldbs-back/file"
        private const val BASE_URL = "http://192.168.0.83:8081/"

        private val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()

        fun <T: Any> getTypedRetrofitInstance(ofClass: Class<T>):T{
            return Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ofClass)
        }
    }
}