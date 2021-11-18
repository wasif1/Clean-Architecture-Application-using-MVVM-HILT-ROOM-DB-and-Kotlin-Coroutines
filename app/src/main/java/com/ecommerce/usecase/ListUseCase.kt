package com.ecommerce.usecase

import com.ecommerce.model.ListResponse
import com.ecommerce.model.ResultData
import com.ecommerce.repository.ListRepository
import javax.inject.Inject

class ListUseCase @Inject constructor(private val repository: ListRepository) {
    suspend fun getList(id: String, cat_id: String): ResultData<ListResponse> {
        val data = repository.getList(id, cat_id)
        return ResultData.Success(data)
    }

    suspend fun getCategoryList(id: String, cat_id: String, sub_cat_id: String): ResultData<ListResponse> {
        val data = repository.getCatList(id, cat_id, sub_cat_id)
        return ResultData.Success(data)
    }
}