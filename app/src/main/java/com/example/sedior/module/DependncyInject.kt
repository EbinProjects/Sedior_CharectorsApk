package com.example.sedior.module

import android.content.Context
import android.content.SharedPreferences
import com.example.sedior.network.ApiServices
import com.example.sedior.repository.ApiRepository


interface DependncyInject {
    val apiServices: ApiServices
    val apiRepository: ApiRepository
    val context:Context
    val sharedPreferences:SharedPreferences
}