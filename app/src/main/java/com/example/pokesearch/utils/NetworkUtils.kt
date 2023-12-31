package com.example.pokesearch.utils

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.pokesearch.model.Abilities
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.model.Stats
import com.example.pokesearch.model.Types
import org.json.JSONObject


private var query = " name = \"pikachu\""
private val TAG = "NetworkUtils"
private var spinning: Boolean = false
fun parsePokemonJsonResult(jsonResult: JSONObject): ArrayList<Pokemon> {

    val pokemonList = ArrayList<Pokemon>()

    val pokemonId = jsonResult.getInt("id")
    val pokemonName = jsonResult.getString("name")
    val pokemonDexNum: Int
    val pokemonTypes: Types
    val pokemonAbilities: Abilities
    //val pokemonMoves = ArrayList<Move>()
    val pokemonStats: Stats

    pokemonDexNum = jsonResult.getJSONObject("species").getString("url")
        .substringAfter("https://pokeapi.co/api/v2/pokemon-species/").dropLast(1).toInt()
    //Log.i(TAG, "the id is $pokemonId and the dexNum is $pokemonDexNum")

    val typeAmt = jsonResult.getJSONArray("types")
    var type1 = ""
    var type2: String? = ""
    if (typeAmt.length() == 1) {
        type1 = jsonResult.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
        type2 = null
    } else if (typeAmt.length() == 2) {
        for (i in 0 until typeAmt.length()) {
            type1 = jsonResult.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
            type2 = jsonResult.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name")
        }
    }
    pokemonTypes = Types(type1, type2)

    val abilityAmt = jsonResult.getJSONArray("abilities")
    val ability1 = abilityAmt.getJSONObject(0).getJSONObject("ability").getString("name")
    val ability2: String?
    val ability3: String?
    if (abilityAmt.length() == 1) {
        ability2 = null
        ability3 = null
    } else if (abilityAmt.length() == 2) {
        ability2 = abilityAmt.getJSONObject(1).getJSONObject("ability").getString("name")
        ability3 = null
    } else {
        ability2 = abilityAmt.getJSONObject(1).getJSONObject("ability").getString("name")
        ability3 = abilityAmt.getJSONObject(2).getJSONObject("ability").getString("name")
    }
    pokemonAbilities = Abilities(ability1, ability2, ability3)

    /*
    val pm = jsonResult.getJSONArray("moves")
    for (i in 0 until pm.length()) {
        val move = pm.getJSONObject(i).getJSONObject("move").getString("name")
        val level = pm.getJSONObject(i).getJSONArray("version_group_details")
            .getJSONObject(0).getInt("level_learned_at")
        pokemonMoves.add(Move(level, move))
   }
   */

    val pokemonSprite = jsonResult.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")

    var hpStat = 0
    var atkStat = 0
    var defStat = 0
    var spAtkStat = 0
    var spDefStat = 0
    var spdStat = 0
    val ps = jsonResult.getJSONArray("stats")
    for (i in 0 until 6) {
        hpStat = ps.getJSONObject(0).getInt("base_stat")
        atkStat = ps.getJSONObject(1).getInt("base_stat")
        defStat = ps.getJSONObject(2).getInt("base_stat")
        spAtkStat = ps.getJSONObject(3).getInt("base_stat")
        spDefStat = ps.getJSONObject(4).getInt("base_stat")
        spdStat = ps.getJSONObject(5).getInt("base_stat")
    }
    pokemonStats = Stats(hpStat, atkStat, defStat, spAtkStat, spDefStat, spdStat)

    pokemonList.add(Pokemon(
        pokemonId,
        pokemonName,
        pokemonDexNum,
        pokemonTypes,
        pokemonAbilities,
        //pokemonMoves,
        pokemonSprite,
        pokemonStats
    ))
    return pokemonList
}

fun parsePokemonFromDex(jsonResult: JSONObject): ArrayList<String> {

    val pokemonNames = ArrayList<String>()

    val a = jsonResult.getJSONArray("pokemon_entries")
    val num = a.length()
    for (i in 0 until num) {
        val name = a.getJSONObject(i).getJSONObject("pokemon_species").getString("name")
        pokemonNames.add(name)
    }
    return pokemonNames
}
fun retrieveQuery(): SimpleSQLiteQuery {
    val fullQuery = "SELECT * FROM pokemondatabase WHERE"
    val orderQuery = " ORDER By dexNum ASC"
    val simpleSQLiteQuery = SimpleSQLiteQuery("$fullQuery$query$orderQuery")
    Log.i(TAG, "the query is: $fullQuery$query")
    return simpleSQLiteQuery
}

fun setQuery(queryString: String) {
    query = queryString
}

