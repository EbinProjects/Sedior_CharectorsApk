package com.example.sedior

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.sedior.module.DependencyModule
import com.example.sedior.viewmodel.CharacterViewmodel
import com.example.sedior.viewmodel.CharecterViewmodelFactory

class MyApplication : Application(), ViewModelStoreOwner {
    override val viewModelStore by lazy { ViewModelStore() }
    lateinit var characterViewmodel: CharacterViewmodel

    companion object{
        lateinit var dependencyInjectModule: DependencyModule
        lateinit var appContext : Context
    }

    override fun onCreate() {
        super.onCreate()
        dependencyInjectModule = DependencyModule(this)
        appContext=this
        initViewModel()

    }

    fun initViewModel() {
        val factory = CharecterViewmodelFactory(
            dependencyInjectModule.apiRepository,
            dependencyInjectModule.context,
            dependencyInjectModule.sharedPreferences,
        )
        characterViewmodel = ViewModelProvider(this, factory)[CharacterViewmodel::class.java]
    }
}