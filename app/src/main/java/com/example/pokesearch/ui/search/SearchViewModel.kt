package com.example.pokesearch.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.repository.PokemonRepository

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val TAG = "SearchViewModel"

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

    private val _paldeaDex = MutableLiveData<List<String>>()
    val paldeaDex: LiveData<List<String>>
        get() = _paldeaDex

    init {
        Log.i(TAG, "init block")
        /*viewModelScope.launch {

            _paldeaDex.value = pokemonRepository.getAllPaldeaPokemonNames()

            Log.d(TAG, "The paldeaDex list size is ${_paldeaDex.value?.size} added _paldea info" )
        }*/
    }

}