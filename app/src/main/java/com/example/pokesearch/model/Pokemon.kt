package com.example.pokesearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val name: String,
    val dexNum: String,
    val types: Types,
    val abilities: Abilities,
    val moves: ArrayList<Move>,
    val sprite: String,
    val stats: Stats
): Parcelable

@Parcelize
data class Abilities(
    val ability1:@RawValue String,
    val ability2: @RawValue String?,
    val ability3: @RawValue String?): Parcelable
@Parcelize
data class Move(val level: @RawValue Int, val name: @RawValue String): Parcelable

@Parcelize
data class Types(val type1: @RawValue String, val type2: @RawValue String?): Parcelable
@Parcelize
data class Stats(
    val hp: @RawValue Int,
    val attack: @RawValue Int,
    val defense: @RawValue Int,
    val specialAttack: @RawValue Int,
    val specialDefense: @RawValue Int,
    val speed: @RawValue Int) : Parcelable

enum class PokemonTypes {
    NORMAL(),
    FIRE(),
    WATER(),
    GRASS(),
    LIGHTNING(),
    ICE(),
    FIGHTING(),
    DRAGON(),
    FLYING(),
    FAIRY(),
    GHOST(),
    PSYCHIC(),
    BUG(),
    GROUND(),
    ROCK(),
    DARK(),
    STEEL(),
    POISON()
}


val availablePokemon = mapOf(

    129 to "magikarp",
    130 to "gyarados",

    200 to "misdreavus",

    296 to "makuhita",
    297 to "hariyama",

    231 to "phanpy",
    232 to "donphan",
    278 to "wingull",
    279 to "pelipper",

    325 to "spoink",
    326 to "grumpig",

    429 to "mismagius",

    443 to "gible",
    444 to "gabite",
    445 to "garchomp",

    739 to "crabrawler",
    740 to "crabominable",
    757 to "salandit",
    758 to "salazzle",

    848 to "arrokuda",
    849 to "barraskewda",
    878 to "cufant",
    879 to "copperajah",

    932 to "nacli",
    933 to "naclstack",
    934 to "garganacl"


)