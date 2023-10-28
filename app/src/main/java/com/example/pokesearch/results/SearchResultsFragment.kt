package com.example.pokesearch.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.pokesearch.databinding.SearchResultsFragmentBinding

class SearchResultsFragment: Fragment() {

    private lateinit var binding: SearchResultsFragmentBinding
    private val searchResultsViewModel by viewModels<SearchResultsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = SearchResultsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchResultsViewModel = searchResultsViewModel

        binding.goToInfoPokemon.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchResultsFragmentDirections.actionSearchResultsFragmentToPokemonInfoFragment()
            )
        }

        return binding.root
    }
}