package com.ecommerce.network

import com.ecommerce.model.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * API END POINTS
     */
    @GET("Categories/{id}/{cat_id}")
    suspend fun getList( @Path("id") id: String?,
                         @Path("cat_id") cat_id: String?): ListResponse

    @GET("Categories/{id}/{cat_id}/{sub_cat_id}")
    suspend fun getCategoriesData(
        @Path("id") id: String?,
        @Path("cat_id") cat_id: String?,
        @Path("sub_cat_id") sub_cat_id: String?
    ): ListResponse

}