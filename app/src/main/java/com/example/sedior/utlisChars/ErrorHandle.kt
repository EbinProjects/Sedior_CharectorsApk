package com.example.sedior.utlisChars

sealed class ErrorHandle<out T> {
    data class Success<out T>(val data: T) : ErrorHandle<T>()
    data class Error(val errorType: ErrorType, val exception: Throwable? = null) : ErrorHandle<Nothing>()
    object Loading : ErrorHandle<Nothing>()

    sealed class ErrorType {
        object Network : ErrorType()
        object Server : ErrorType()
        object Unauthorized : ErrorType()
        object NotFound : ErrorType()
        object Unknown : ErrorType()
    }
}