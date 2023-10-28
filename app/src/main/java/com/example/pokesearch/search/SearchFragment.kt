package com.example.pokesearch.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.databinding.SearchFragmentBinding
import com.example.pokesearch.utils.parsePokemonJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class SearchFragment: Fragment() {

    val  TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = SearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchViewModel = searchViewModel

        binding.searchToAdvanced.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchFragmentToAdvancedSearchFragment())
        }
        binding.searchToSearchResults.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchFragmentToSearchResultsFragment()
            )
        }








        return binding.root
    }


}