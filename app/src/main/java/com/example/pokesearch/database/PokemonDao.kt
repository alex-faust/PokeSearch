package com.example.pokesearch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDao {

    /**
     * Single search pokemon
     */
    @Query("select * FROM pokemondatabase WHERE name = :name")
    fun getSinglePokemonByName(name: String): LiveData<List<PokemonDatabase>>

    @Query("select * FROM pokemondatabase")
    fun getAllPaldeaPokemon(): LiveData<List<PokemonDatabase>>

    //@Query("select * FROM pokemondatabase")
    //fun getAllKitakamiPokemon(): LiveData<List<String>>

    @Upsert
    fun insertAllPokemon(vararg pokemon: PokemonDatabase)


    /**
     * Advanced search pokemon
     */
    //fun getPokemonByName(name: String): LiveData<List<PokemonDatabase>>

    //@Upsert
    //fun insertPokemon(vararg pokemon: Pokemon)

    //@Query("delete from pokemondatabase")
    //fun deleteAllPokemonFromDB()

    //TODO look up @Upsert
    //TODO look up Flow. it's an observable that lets you know the changes like live data

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //@Upsert
    //fun insertPokemon(vararg pokemon: Pokemon)

    //type queries
    //select all pokemon where type1 is X or type 2 is x
    //select all pokemon where type 1 is x and type 2 is x

    //ability queries
    //select all pokemon where ability is x

    //moves queries
    //select all pokemon where move is x

    //stats queries
    //select all pokemon where specific stat is x
    //select all pokemon where specific stat is > x

}
