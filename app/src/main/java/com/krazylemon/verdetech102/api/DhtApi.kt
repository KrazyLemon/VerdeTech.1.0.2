package com.krazylemon.verdetech102.api

import com.krazylemon.verdetech102.models.DhtModel
import com.krazylemon.verdetech102.models.OutputList
import com.krazylemon.verdetech102.models.UpdatedModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DhtApi {
    @GET("/dht-api.php")
    suspend fun getDht(
        @Query("action") action : String,
        @Query("limit") limit : Int,
    ) : Response<DhtModel>

    @GET("/dht-api.php")
    suspend fun getDhtByDate(
        @Query("action") action : String,
        @Query("first_date") first_date : String,
        @Query("second_date") second_date : String
    ) : Response<DhtModel>

    @GET("/output-app.php")
    suspend fun updateState(
        @Query("action") action : String,
        @Query("id") id : Int,
        @Query("state") state : Int
    ) : Response<UpdatedModel>

    @GET("/output-app.php")
    suspend fun getOutputsState(
        @Query("action") action : String,
        @Query("board") board : Int
    ) : Response<OutputList>

}

