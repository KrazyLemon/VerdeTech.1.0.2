package com.krazylemon.verdetech102.api

//T se refiere al DHT Model
sealed class NetworkResponse <out T>{
    data class Success<out T>(val data : T):NetworkResponse<T>()
    data class Error(val message: String): NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}