package com.example.pokesearch.repository

interface DefaultPokemonRepository {
    suspend fun getPokemonByName(name: String)
}