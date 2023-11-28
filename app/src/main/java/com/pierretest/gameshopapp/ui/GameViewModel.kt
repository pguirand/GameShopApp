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

const val TAG = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(private val repository : Repository) : ViewModel(){

    private val _listAllGames = MutableStateFlow<List<SingleGameModel>>(emptyList())

    private val _OneGame = MutableStateFlow<SingleGameModel?>(null)
    init {
        getAllGames()
    }

    val listAllGames : StateFlow<List<SingleGameModel>> = _listAllGames
    val OneGame : StateFlow<SingleGameModel?> = _OneGame

    fun getAllGames() {
        viewModelScope.launch {
            try {
                val gamelist = repository.getAllGames()
                _listAllGames.value = gamelist
            } catch (e : Exception) {
                Log.e(TAG, e.message ?: "unexpected error occured")
            }
        }
    }



    fun getGameById(gameId:Int) : SingleGameModel? {
        return _listAllGames.value.find { it.id == gameId }
    }


    fun getOneGameById(gameId:Int) {
        viewModelScope.launch {
            try {
                val value = repository.getSingleGameById(gameId)
                _OneGame.value = value
            } catch (e : Exception) {
                Log.e(TAG, e.message ?: "unexpected error occured")
                _OneGame.value = null
            }
        }

//        return _listAllGames.value.find { it.id == gameId }
    }

    fun clearGM(){
        _OneGame.value = null
    }
}