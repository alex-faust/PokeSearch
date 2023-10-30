package com.example.pokesearch.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokesearch.databinding.SearchResultsFragmentBinding
import com.example.pokesearch.utils.PokemonRVAdapter

class SearchResultsFragment: Fragment() {

    private lateinit var binding: SearchResultsFragmentBinding
    private val resultsViewModel by viewModels<SearchResultsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = SearchResultsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchResultsViewModel = resultsViewModel

        binding.pokemonRecycler.adapter = PokemonRVAdapter(PokemonRVAdapter.OnClickListener {
            resultsViewModel.displayPokemonDetails(it)
        })

        resultsViewModel.navigateToSelectedPokemon.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(SearchResultsFragmentDirections
                    .actionResultsToInfo(it))
                resultsViewModel.displayPokemonDetailsComplete()

            }
        }




        return binding.root
    }
}