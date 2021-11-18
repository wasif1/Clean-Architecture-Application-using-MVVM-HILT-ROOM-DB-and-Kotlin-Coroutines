package com.ecommerce.repository

import com.ecommerce.model.ListResponse
import com.ecommerce.network.ApiService
import javax.inject.Inject

class ListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getList(id: String, cat_id: String): ListResponse {
        return apiService.getList(id, cat_id)
    }

    suspend fun getCatList(id: String, cat_id: String, sub_cat_id: String): ListResponse {
        return apiService.getCategoriesData(id, cat_id, sub_cat_id)
    }
}