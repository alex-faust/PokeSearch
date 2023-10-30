package com.example.pokesearch.api

import com.example.pokesearch.database.PokemonDatabase
import com.example.pokesearch.model.Abilities
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.model.Stats
import com.example.pokesearch.model.Types
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkPokemonContainer(val pokemon: NetworkPokemon)

@JsonClass(generateAdapter = true)
data class NetworkPokemon(
    val name: String,
    val dexNum: String,
    val types: Types,
    val abilities: Abilities,
    val sprite: String,
    val stats: Stats
)

fun NetworkPokemonContainer.asDomainModel(): Pokemon {
    return Pokemon (
        name = this.pokemon.name,
        dexNum = this.pokemon.dexNum,
        types = this.pokemon.types,
        abilities = this.pokemon.abilities,
        sprite = this.pokemon.sprite,
        stats = this.pokemon.stats
    )
}

fun Pokemon.asDatabaseModel(): Pokemon {
    return Pokemon(
        name = this.name,
        dexNum = this.dexNum,
        types = this.types,
        abilities = this.abilities,
        sprite = this.sprite,
        stats = this.stats
    )
}
//-----------------------------------Advanced Search---------------------------------------

@JsonClass(generateAdapter = true)
data class NetworkPokemonContainers(val pokemon: List<NetworkPokemon>)

fun NetworkPokemonContainers.asDomainModel(): List<Pokemon> {
    return pokemon.map{
        Pokemon (
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            sprite = it.sprite,
            stats = it.stats
        )
    }

}

fun List<Pokemon>.asDatabaseModel(): Array<PokemonDatabase> {
    return map {
        PokemonDatabase (
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            sprite = it.sprite,
            stats = it.stats)
    }.toTypedArray()
}

fun List<PokemonDatabase>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            //moves = this.moves,
            sprite = it.sprite,
            stats = it.stats
        )
    }
}








