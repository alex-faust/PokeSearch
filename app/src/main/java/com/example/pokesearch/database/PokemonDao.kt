package com.example.pokesearch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface PokemonDao {

    @Query("select * FROM pokemondatabase WHERE name = :name")
    fun getPokemonByName(name: String): LiveData<List<PokemonDatabase>>

    @Query("select * FROM pokemondatabase")
    fun getAllPaldeaPokemon(): LiveData<List<PokemonDatabase>>

    @RawQuery(observedEntities = [PokemonDatabase::class])
    fun getPokemon(query: SupportSQLiteQuery): LiveData<List<PokemonDatabase>>


    @Upsert
    fun insertPokemon(vararg pokemon: PokemonDatabase)

}
