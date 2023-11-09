package com.example.pokesearch.database

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokesearch.model.Abilities
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.model.Stats
import com.example.pokesearch.model.Types


@Entity
data class PokemonDatabase(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name")      val name: String,
    @ColumnInfo(name = "dexNum")    val dexNum: Int,
    @Embedded val types: Types,
    @Embedded val abilities: Abilities,
    //@Embedded val moves: ArrayList<Move>,
    @ColumnInfo(name = "sprite")       val sprite: String,
    @Embedded val stats: Stats
)

/*fun PokemonDatabase.asDomainModel(): Pokemon {
    return Pokemon (
        name = name,
        dexNum = dexNum,
        types = types,
        abilities = abilities,
        sprite = sprite,
        stats = stats
    )
}*/

fun List<PokemonDatabase>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            id = it.id,
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            sprite = it.sprite,
            stats = it.stats
        )
    }
}

fun List<PokemonDatabase>.asArrayList(): ArrayList<String> {
    val allNames = ArrayList<String>()
    for (i in indices) {
        allNames.add(this[i].name)
        Log.d("DatabaseEntities", "Added is ${this[i]}")
    }
    return allNames
}