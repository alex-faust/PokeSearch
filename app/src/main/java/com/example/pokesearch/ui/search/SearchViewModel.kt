package com.example.pokesearch.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.repository.PokemonRepository
import kotlinx.coroutines.launch

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val TAG = "SearchViewModel"

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

    val pokemonSearched: LiveData<List<Pokemon>> = pokemonRepository.pokemonResult

    init {
        Log.i(TAG, "init block")
        viewModelScope.launch {
            //TODO(issue #1)
            //val names = getApplication<Application>().resources.getStringArray(R.array.names_for_adapter)
            //pokemonRepository.addAllPokemonToDatabase(names)
        }

    }

}
