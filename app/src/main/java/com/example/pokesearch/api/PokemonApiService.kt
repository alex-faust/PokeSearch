package com.example.pokesearch.api

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
const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PokemonApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name")pokemonName: String): String

}

object PokemonApi {
    val pokemonRetrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}