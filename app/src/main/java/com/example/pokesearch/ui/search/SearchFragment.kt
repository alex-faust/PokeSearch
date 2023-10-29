package com.example.pokesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pokesearch.databinding.SearchFragmentBinding

class SearchFragment: Fragment() {

    val  TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding
    //private val searchViewModel by viewModels<SearchViewModel>()
   private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

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