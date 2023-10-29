package com.example.pokesearch.api

import com.example.pokesearch.model.Abilities
import com.example.pokesearch.model.Move
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.model.Stats
import com.example.pokesearch.model.Types
import com.squareup.moshi.JsonClass

/*
@JsonClass(generateAdapter = true)
data class NetworkPokemonContainer(val pokemon: NetworkPokemon)*/

/*@JsonClass(generateAdapter = true)
data class NetworkPokemon(
    val name: String,
    val dexNum: String,
    val types: Types,
    val abilities: Abilities,
    val moves: ArrayList<Move>,
    val sprite: String,
    val stats: Stats
)*/

/*fun NetworkPokemonContainer.asDomainModel(): Pokemon {
    return Pokemon(
        name = pokemon.name,
        dexNum = pokemon.dexNum,
        types = pokemon.types,
        //abilities = pokemon.abilities,
        //moves = pokemon.moves,
        //sprite = pokemon.sprite,
        //stats = pokemon.stats
    )

}*/

/*fun LiveData<PokemonDatabase>.asDomainModel(): Pokemon {
    return Pokemon(
        name = name,
        dexNum = this.dexNum,
        //types = this.types,
        //abilities = this.abilities,
        //moves = this.moves,
        //sprite = this.sprite,
        //stats = this.stats
    )
}*/


fun Pokemon.asDatabaseModel(): Pokemon {
    return Pokemon(
        name = this.name,
        dexNum = this.dexNum,
        types = this.types,
        abilities = this.abilities,
        //moves = this.moves,
        sprite = this.sprite,
        stats = this.stats
    )
}

  /*  fun PokemonDatabase.asDomainModel() : Pokemon {
        return Pokemon(
            name = this.name,
            dexNum = this.dexNum,
            //types = this.types,
            //abilities = this.abilities,
            //moves = this.moves,
            //sprite = this.sprite,
            //stats = this.stats
        )
    }*/







