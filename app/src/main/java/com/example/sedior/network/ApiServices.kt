package com.example.sedior.network
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

//Lazy which means the lambda inside lazy is only executed when lazyValue is first accessed.
interface ApiServices {

    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): ResultsItem


}