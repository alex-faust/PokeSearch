package com.example.pokesearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PokemonDatabase::class], version = 1, exportSchema = false)
abstract class PokeDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

@Volatile
private lateinit var INSTANCE: PokeDatabase

fun getDatabase(context: Context): PokeDatabase {
    synchronized(PokeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PokeDatabase::class.java, "poke.DB"
            ).build()
        }
    }
    return INSTANCE
}


