package com.mysoft.uldbsmarket.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    companion object {
        public const val IMAGE_DOWNLOAD_URL = "http://192.168.0.82:8081/uldbs-back/file"
        private const val BASE_URL = "http://192.168.0.82:8081/"
        private val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()

        //TODO refactor defrred T шаблон использовать
        fun getUserRetrofitInstance(): UserAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(UserAPI::class.java)
        }

        fun getChatRetrofitInstance(): ChatAPI{
            return Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ChatAPI::class.java)
        }

        fun getMessageRetrofitInstance(): MessageAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(MessageAPI::class.java)
        }

        fun getGoodRetrofitInstance(): GoodAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(GoodAPI::class.java)
        }
    }
}