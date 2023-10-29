package com.example.pokesearch.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.repository.PokemonRepository
import kotlinx.coroutines.launch

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val TAG = "Cynthia"

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

    init {
        viewModelScope.launch {
            pokemonRepository.savePokemonToDB()

            //pokemonRepository.pokemonByName
        }
    }
}