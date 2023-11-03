package com.example.pokesearch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface PokemonDao {

    /**
     * Single search pokemon
     */
    @Query("select * FROM pokemondatabase WHERE name = :name")
    fun getPokemonByName(name: String): LiveData<List<PokemonDatabase>>

    //fun getSinglePokemonByName(name: String): LiveData<List<PokemonDatabase>>

    @Query("select * FROM pokemondatabase")
    fun getAllPaldeaPokemon(): LiveData<List<PokemonDatabase>>

    //@Query("select * FROM pokemondatabase")
    //fun getAllKitakamiPokemon(): LiveData<List<String>>

    //TODO(issue #3)
    @RawQuery(observedEntities = [PokemonDatabase::class])
    fun getPokemon(query: SupportSQLiteQuery): LiveData<List<PokemonDatabase>>


    @Upsert
    fun insertPokemon(vararg pokemon: PokemonDatabase)

   // @Upsert
    //fun insertAllPokemon(vararg pokemon: LiveData<List<Pokemon>>)



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
