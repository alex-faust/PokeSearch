package com.example.pokesearch.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokesearch.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Pokemon (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name")      val name: String,
    @ColumnInfo(name = "dexNum")    val dexNum: Int,
    @Embedded val types: Types,
    @Embedded val abilities: Abilities,
    //@Embedded val moves: ArrayList<Move>,
    @ColumnInfo(name = "sprite")       val sprite: String,
    @Embedded val stats: Stats

): Parcelable

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
    @ColumnInfo(name = "hpStat")    val hp: Int,
    @ColumnInfo(name = "atkStat")   val attack: Int,
    @ColumnInfo(name = "defStat")   val defense: Int,
    @ColumnInfo(name = "spAtkStat") val specialAttack: Int,
    @ColumnInfo(name = "spDefStat") val specialDefense: Int,
    @ColumnInfo(name = "spdStat")   val speed: Int) : Parcelable

enum class PokemonTypes(val type: String, val drawable: Int) {
    BUG("bug", R.drawable.bug),
    DARK("dark", R.drawable.dark),
    DRAGON("dragon", R.drawable.dragon),
    ELECTRIC("electric", R.drawable.electric),
    FAIRY("fairy", R.drawable.fairy),
    FIGHTING("fighting", R.drawable.fighting),
    FIRE("fire", R.drawable.fire),
    FLYING("flying", R.drawable.flying),
    GHOST("ghost", R.drawable.ghost),
    GRASS("grass", R.drawable.grass),
    GROUND("ground", R.drawable.ground),
    ICE("ice", R.drawable.ice),
    NORMAL("normal", R.drawable.normal),
    POISON("poison", R.drawable.poison),
    PSYCHIC("psychic", R.drawable.psychic),
    ROCK("rock", R.drawable.rock),
    STEEL("steel", R.drawable.steel),
    WATER("water", R.drawable.water)

}