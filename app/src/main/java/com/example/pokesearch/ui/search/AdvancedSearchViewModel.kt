package com.example.pokesearch.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.repository.PokemonRepository

class AdvancedSearchViewModel(app: Application) : AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val pokemonRepository = PokemonRepository(database)

    private lateinit var query: SimpleSQLiteQuery
    /*val query: LiveData<SimpleSQLiteQuery>
        get() = _query*/

    //TODO(issue #3)
    val queryString = SimpleSQLiteQuery("SELECT * FROM pokemondatabase WHERE type1 = fire")

    fun getQuery() {
        query = SimpleSQLiteQuery("SELECT * FROM pokemondatabase WHERE type1 = fire OR type2 = fire")
        //_query = database.pokemonDao.getPokemon(queryString)
    }

}