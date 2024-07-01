package com.krazylemon.verdetech102.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DhtApi {
    @GET("/dht-api.php")
    suspend fun getDht(
        @Query("limit") limit : Int,
    ) : Response<DhtModel>
}

