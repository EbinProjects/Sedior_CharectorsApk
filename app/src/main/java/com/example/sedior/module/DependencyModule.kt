package com.example.sedior.module

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.sedior.network.ApiServices
import com.example.sedior.repository.ApiRepository
import com.example.sedior.utlisChars.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class DependencyModule(appContext: Context): DependncyInject {
    private var baseUrl = Constants.BASE_URL

    override val apiServices: ApiServices by lazy {
        createApiServices(baseUrl)
    }
    fun createApiServices(baseUrl:String):ApiServices{
        val logger = HttpLoggingInterceptor { message ->
            Log.d("ApiServices" , "createAccessFlow: $message")
        }
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Adjust timeout as needed
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS) .addInterceptor(logger).build()
        return   Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    override val apiRepository: ApiRepository by lazy {
        ApiRepository(apiServices)
    }


    override val context: Context by lazy {
        appContext
    }
    override val sharedPreferences: SharedPreferences by lazy {
        appContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

}