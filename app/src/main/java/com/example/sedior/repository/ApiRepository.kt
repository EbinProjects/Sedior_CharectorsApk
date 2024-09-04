package com.example.sedior.repository

import com.example.sedior.network.ApiServices
import com.example.sedior.network.CharacterResponse
import com.example.sedior.network.ResultsItem
import com.example.sedior.utlisChars.ErrorHandle
import com.example.sedior.viewmodel.CharacterViewmodel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ApiRepository(private val apiServices: ApiServices) {

    fun searchChars(page : Int): Flow<ErrorHandle<CharacterResponse>> = flow {
        emit(ErrorHandle.Loading)
        val response = apiServices.getCharacters(page)
        emit(ErrorHandle.Success(response))
    }.catch { e ->
        emit(ErrorHandle.Error(mapToErrorType(e), e))
    }
    fun searchCharacter(id : String): Flow<ErrorHandle<ResultsItem>> = flow {
        emit(ErrorHandle.Loading)
        val response = apiServices.getCharacter(id)
        emit(ErrorHandle.Success(response))
    }.catch { e ->
        emit(ErrorHandle.Error(mapToErrorType(e), e))
    }

    private fun mapToErrorType(exception: Throwable): ErrorHandle.ErrorType {
        return when (exception) {
            is IOException -> ErrorHandle.ErrorType.Network
            is HttpException -> {
                when (exception.code()) {
                    401 -> ErrorHandle.ErrorType.Unauthorized
                    404 -> ErrorHandle.ErrorType.NotFound
                    in 500..599 -> ErrorHandle.ErrorType.Server
                    else -> ErrorHandle.ErrorType.Unknown
                }
            }
            else -> ErrorHandle.ErrorType.Unknown
        }
    }


}