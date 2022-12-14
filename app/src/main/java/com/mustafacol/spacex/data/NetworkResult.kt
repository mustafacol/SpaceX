package com.mustafacol.spacex.data

sealed class NetworkResult<out T>{
    class Success<T>(val data: T) : NetworkResult<T>()
    class Failure(val throwable: Throwable) : NetworkResult<Nothing>()
}
