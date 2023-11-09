package com.example.pokesearch.ui.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.pokesearch.R
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.repository.PokemonRepository
import com.example.pokesearch.utils.GeofenceUtils

class GameViewModel(app: Application) : AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

   /* private val _geofenceIndex = state.getLiveData(GEOFENCE_INDEX_KEY, -1)
    private val _hintIndex = state.getLiveData(HINT_INDEX_KEY, 0)
    val geofenceIndex: LiveData<Int>
        get() = _geofenceIndex

    val geofenceHintResourceId = geofenceIndex.map {
        val index = geofenceIndex.value ?: -1
        when {
            index < 0 -> R.string.not_started_hint
            index < GeofenceUtils.NUM_LANDMARKS ->
                GeofenceUtils.SF_LANDMARK_DATA[geofenceIndex.value!!].hint
            else -> R.string.geofence_over
        }
    }

    val geofenceImageResourceId = geofenceIndex.map {
        val index = geofenceIndex.value ?: -1
        when {
            index < GeofenceUtils.NUM_LANDMARKS -> R.drawable.poke_ball
            else -> R.drawable.poke_ball
        }
    }

    fun updateHint(currentIndex: Int) {
        _hintIndex.value = currentIndex+1
    }

    fun geofenceActivated() {
        _geofenceIndex.value = _hintIndex.value
    }

    fun geofenceIsActive() =_geofenceIndex.value == _hintIndex.value
    fun nextGeofenceIndex() = _hintIndex.value ?: 0
*/
    val randomPokemonResult: LiveData<List<Pokemon>> = pokemonRepository.pokemonResult
}

private const val HINT_INDEX_KEY = "hintIndex"
private const val GEOFENCE_INDEX_KEY = "geofenceIndex"