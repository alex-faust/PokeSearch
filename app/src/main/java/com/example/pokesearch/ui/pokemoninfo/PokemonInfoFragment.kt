package com.example.pokesearch.ui.pokemoninfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokesearch.databinding.PokemonInfoFragmentBinding

class PokemonInfoFragment : Fragment() {

    private lateinit var binding: PokemonInfoFragmentBinding
    private val pokeViewModel by viewModels<PokemonInfoViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = PokemonInfoFragmentBinding.inflate(inflater)
        binding.pokemonViewModel = pokeViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner



        return binding.root
    }
}