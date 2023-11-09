package com.example.pokesearch.ui.pokemoninfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.model.Pokemon
import kotlinx.coroutines.launch

class PokemonInfoViewModel : ViewModel() {

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _atkStat = MutableLiveData("ATK")
    var atkStat: LiveData<String> = _atkStat
        //get() = _atkStat

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