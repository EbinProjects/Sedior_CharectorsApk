package com.example.sedior.utlisChars

sealed class Screens(val route:String){
    data object loginScreen : Screens("loginScreen")
    data object homeScreen : Screens("homeScreen")
    data object Details : Screens("Details")
    data object Splash : Screens("Splash")

    fun withArguments(vararg args :String):String{
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}