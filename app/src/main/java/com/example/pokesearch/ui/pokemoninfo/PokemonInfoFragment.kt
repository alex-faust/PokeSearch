package com.example.pokesearch.ui.pokemoninfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokesearch.databinding.PokemonInfoFragmentBinding

class PokemonInfoFragment : Fragment() {

    private lateinit var binding: PokemonInfoFragmentBinding
    //private val pokeViewModel by viewModels<PokemonInfoViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PokemonInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedPokemon = PokemonInfoFragmentArgs.fromBundle(requireArguments()).selectedPokemon

        binding.pokemon = selectedPokemon

        return binding.root
    }
}