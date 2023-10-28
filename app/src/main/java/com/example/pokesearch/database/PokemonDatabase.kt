package com.example.pokesearch.database

import androidx.lifecycle.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokesearch.model.Move
import com.example.pokesearch.model.Pokemon

/*@Entity
data class PokemonDatabase(
    @PrimaryKey
    val name: String,
    val dexNum: Int,
    val types: ArrayList<String?>,
    val abilities: ArrayList<String?>,
    val moves: ArrayList<Move>,
    val sprite: String,
    val stats: ArrayList<String?>
)*/

/*
fun PokemonDatabase.asDomainModel(): Pokemon {
    return map {
        Pokemon(
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            moves = it.moves,
            sprite = it.sprite,
            stats = it.stats
        )
    }
}*/
