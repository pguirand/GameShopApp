package com.pierretest.gameshopapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel
import com.pierretest.gameshopapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CategoryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _listAllCategories = MutableStateFlow<List<String>>(emptyList())
    private val _singleCategory = MutableStateFlow<List<SingleGameModel>>(emptyList())
    private val _isLoaded = MutableStateFlow(false)


    init {
        getAllCategories()
    }

    val listAllCategories : StateFlow<List<String>> = _listAllCategories
    val singleCategory : StateFlow<List<SingleGameModel>> = _singleCategory
    val isLoaded : StateFlow<Boolean> = _isLoaded // Expose the isLoaded state to the view

    fun getSingleCategory(catName : String) {
        viewModelScope.launch {
            try {
                val singleCat = repository.getCategoryByName(catName)
                _singleCategory.value = singleCat
                _isLoaded.value = true // Data is loaded when a single category is fetched
            } catch (e : Exception) {
                Log.e("SingleCat", e.message?: "Unexpected Error")
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            try {
                val catlist = repository.getAllCategories()
                _listAllCategories.value = catlist as List<String>
                _isLoaded.value = true
            } catch (e : Exception) {
                Log.e("Catlist", e.message?: "Unexpected Error")
            }
        }
    }


}