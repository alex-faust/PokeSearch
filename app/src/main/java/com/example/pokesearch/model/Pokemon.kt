package com.example.pokesearch.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Pokemon (
    @PrimaryKey
    @ColumnInfo(name = "name")      val name: String,
    @ColumnInfo(name = "dexNum")    val dexNum: String,
    @Embedded val types: Types,
    @Embedded val abilities: Abilities,
    //@Embedded val moves: ArrayList<Move>,
    @ColumnInfo(name = "sprite")       val sprite: String,
    @Embedded val stats: Stats

): Parcelable {
    companion object {
        override fun toString(): String {
            //val mon: Pokemon
            return ""
        }
    }
}

@Parcelize
data class Abilities(
    @ColumnInfo(name = "ability1")val ability1: String,
    @ColumnInfo(name = "ability2")val ability2: String?,
    @ColumnInfo(name = "ability3")val ability3: String?
): Parcelable

@Parcelize
data class Move(
    @ColumnInfo(name = "moveLevel") val moveLevel: Int,
    @ColumnInfo(name = "moveName") val moveName: String
): Parcelable

@Parcelize
data class Types(
    @ColumnInfo(name = "type1") val type1: String,
    @ColumnInfo(name = "type2") val type2: String?
): Parcelable

@Parcelize
data class Stats(
    @ColumnInfo(name = "hpStat")    val hp: String,
    @ColumnInfo(name = "atkStat")   val attack: String,
    @ColumnInfo(name = "defStat")   val defense: String,
    @ColumnInfo(name = "spAtkStat") val specialAttack: String,
    @ColumnInfo(name = "spDefStat") val specialDefense: String,
    @ColumnInfo(name = "spdStat")   val speed: String) : Parcelable

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

/*
val name: String,
    val dexNum: String,
    val types: Types,
    //val abilities: Abilities,
    //val moves: ArrayList<Move>,
    //val sprite: String,
    //val stats: Stats
 */