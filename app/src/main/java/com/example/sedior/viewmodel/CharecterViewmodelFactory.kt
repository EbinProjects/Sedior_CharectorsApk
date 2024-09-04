package com.example.sedior.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sedior.repository.ApiRepository


class CharecterViewmodelFactory(
    private val apiRepository: ApiRepository,
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CharacterViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewmodel(
                apiRepository,
                context,
                sharedPreferences,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}