package com.example.pokesearch.ui.results

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.repository.PokemonRepository
import kotlinx.coroutines.launch

class SearchResultsViewModel(app: Application): AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

    private val _navigateToSelectedPokemon = MutableLiveData<Pokemon?>()

    val navigateToSelectedPokemon: MutableLiveData<Pokemon?>
        get() = _navigateToSelectedPokemon

    init {
        viewModelScope.launch {
            //pokemonRepository.savePokemonToDB()
            //TODO(issue #2)
            pokemonRepository.pokemonResult

        }
    }

    fun displayPokemonDetails(pokemon: Pokemon) {
        _navigateToSelectedPokemon.value = pokemon
    }

    fun displayPokemonDetailsComplete() {
        _navigateToSelectedPokemon.value = null
    }

    val pokemonList: LiveData<List<Pokemon>> = pokemonRepository.pokemonResult



}

