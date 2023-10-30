package com.example.pokesearch.database

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
    @ColumnInfo(name = "name")      val name: String,
    @ColumnInfo(name = "dexNum")    val dexNum: String,
    @Embedded val types: Types,
    @Embedded val abilities: Abilities,
    //@Embedded val moves: ArrayList<Move>,
    @ColumnInfo(name = "sprite")       val sprite: String,
    @Embedded val stats: Stats
)

fun PokemonDatabase.asDomainModel(): Pokemon {
    return Pokemon (
        name = this.name,
        dexNum = this.dexNum,
        types = this.types,
        abilities = this.abilities,
        sprite = this.sprite,
        stats = this.stats
    )
}

//------------------------Advanced Search ----------------------------------------
fun List<PokemonDatabase>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            name = it.name,
            dexNum = it.dexNum,
            types = it.types,
            abilities = it.abilities,
            sprite = it.sprite,
            stats = it.stats
        )
    }
}