package com.ecommerce.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.Utils.Constants
import com.ecommerce.model.ListResponse
import com.ecommerce.model.ResultData
import com.ecommerce.usecase.ListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private var usecase: ListUseCase) : ViewModel() {

    private val _response: MutableLiveData<ResultData<ListResponse>> = MutableLiveData()
    val response: LiveData<ResultData<ListResponse>> = _response

    private val _response2: MutableLiveData<ResultData<ListResponse>> = MutableLiveData()
    val response2: LiveData<ResultData<ListResponse>> = _response2

    fun listItems() = viewModelScope.launch {
        _response.value = usecase.getList(Constants.cat_api_id, Constants.cat_id)
    }

    fun catListItems(category : String) = viewModelScope.launch {
        _response2.value = usecase.getCategoryList(Constants.cat_api_id, Constants.cat_id, category)
    }
}