package com.example.pokesearch.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Upsert
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.utils.TypesConverters

@Dao
interface PokemonDao {

    @Query("select * FROM pokemon WHERE name = :name")
    fun getPokemonByName(name: String): LiveData<Pokemon>

    @Query("delete from pokemon")
    fun deleteAllPokemonFromDB()

    //TODO look up @Upsert
    //TODO look up Flow. it's an observable that lets you know the changes like live data

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Upsert
    fun insertPokemon(pokemon: Pokemon)

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
