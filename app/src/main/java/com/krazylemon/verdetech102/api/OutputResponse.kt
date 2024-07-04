package com.krazylemon.verdetech102.api

sealed class OutputResponse <out Y> {
    data class Success<out Y>(val data: Y) : OutputResponse<Y>()
    data class Error(val message: String) : OutputResponse<Nothing>()
    object Loading : OutputResponse<Nothing>()
}