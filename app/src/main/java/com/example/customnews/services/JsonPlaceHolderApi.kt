package com.example.customnews.services

import com.example.customnews.data.Post
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val apiKey = "dlAmtYCP6UbvGpnJXguK1iDoGTA6J94E"

interface JsonPlaceHolderApi {

//    mostpopular/v2/shared/1/twitter.json
//    topstories/v2/home.json
//    topstories/v2/business.json

    @GET("topstories/v2/home.json")
    suspend fun getPostList(): retrofit2.Response<Post>

    companion object {
        operator fun invoke(): JsonPlaceHolderApi {

            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api-key", apiKey)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val myOkHttp :OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            val myRetrofit = Retrofit.Builder()
                .client(myOkHttp)
                .baseUrl("https://api.nytimes.com/svc/")
                //.callAdapterFactories()
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return myRetrofit.create(JsonPlaceHolderApi::class.java)
        }
    }
}