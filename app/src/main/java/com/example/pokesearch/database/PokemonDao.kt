package com.example.pokesearch.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface PokemonDao {

    //@Query("")
    //fun getPokemon

    /*
    @Query("select * FROM asteroiddatabase WHERE closeApproachDate > :currentDate ORDER BY closeApproachDate ASC")
    fun getWeeksAsteroids(currentDate: String): LiveData<List<AsteroidDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidDatabase)

    @Query("delete from asteroiddatabase")
    fun removeAllAsteroidsFromDB()

    @Query("select * from asteroiddatabase where closeApproachDate = :currentDate")
    fun getTodayAsteroids(currentDate: String): LiveData<List<AsteroidDatabase>>

    @Query("select * FROM asteroiddatabase WHERE closeApproachDate >= :currentDate ORDER BY closeApproachDate ASC")
    fun getSavedAsteroids(currentDate: String): LiveData<List<AsteroidDatabase>>
     */
}

/*
@Database(entities = [PokemonDatabase::class], version = 1)
abstract class DatabasePokemon: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

private lateinit var INSTANCE: DatabasePokemon

fun getDatabase(context: Context): DatabasePokemon {
    synchronized(DatabasePokemon::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DatabasePokemon::class.java, "pokemonDB"
            ).build()
        }
    }
    return INSTANCE
}*/
