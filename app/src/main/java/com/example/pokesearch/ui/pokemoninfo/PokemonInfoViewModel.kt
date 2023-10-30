package com.example.pokesearch.ui.pokemoninfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.model.Pokemon
import kotlinx.coroutines.launch

class PokemonInfoViewModel(pokemon: Pokemon, app: Application) : AndroidViewModel(app) {




    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    init {
        //getPokemonName()
    }

    private fun getPokemonName() {
        viewModelScope.launch {
            try {
                //val jsonResult = PokemonApi.pokemonRetrofitService.getPokemonById(915)
                //val poke = parsePokemonJsonResult(JSONObject(jsonResult))
               // _pokemon.value = poke
                //Log.i("Pokemon", "The name is ${_pokemon.value.sprite}")
            } catch (e: Exception) {
                e.localizedMessage
            }
        }
    }




}