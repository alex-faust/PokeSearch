package com.example.pokesearch.api

import com.example.pokesearch.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Gets item from the pokemon api
 */

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface PokemonApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name")pokemonName: String): String

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id")pokemonDexNum: Int): String

    @GET("pokemon/{value}")
    suspend fun getPokemonByType(@Path("value")pokemon: String): String

    @GET("pokedex/{value}")
    suspend fun getAllPokemonAbility(@Path("value")pokemon: String): String

    @GET("pokemon/{value}")
    suspend fun getPokemonByMove(@Path("value")pokemon: Int): String

    @GET("pokemon/{value}")
    suspend fun getPokemonByStat(@Path("value")pokemon: String): String

    @GET("pokedex/{value}")
    suspend fun getDex(@Path("value")region: Int): String

    //@GET("pokedex/32")
    //fun getAllKitakamiPokemonNames(): ArrayList<String>

    //get all pokemon in the paldea pokedex
    //https://pokeapi.co/api/v2/pokedex/31/
    //get all pokemon in the kitakami pokedex
    //https://pokeapi.co/api/v2/pokedex/32/


}

object PokemonApi {
    val pokemonRetrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}