package com.example.pokesearch.api

import com.example.pokesearch.model.Move
import com.example.pokesearch.model.Pokemon
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkPokemonContainer(val pokemon: NetworkPokemon)

@JsonClass(generateAdapter = true)
data class NetworkPokemon(
    val name: String,
    val dexNum: Int,
    val types: ArrayList<String?>,
    val abilities: ArrayList<String?>,
    val moves: ArrayList<Move>,
    val sprite: String,
    val stats: ArrayList<String?>
)

/*fun NetworkPokemonContainer.asDomainModel(): Pokemon {
    return pokemon.map {
        Pokemon (
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

/*
fun Pokemon.asDatabaseModel(): PokemonDatabase {
    return map {
        PokemonDatabase (
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
