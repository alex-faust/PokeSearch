package com.example.pokesearch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokesearch.databinding.AdvancedSearchFragmentBinding
import com.example.pokesearch.search.SearchFragmentDirections

class AdvancedSearchFragment: Fragment() {

    private lateinit var binding: AdvancedSearchFragmentBinding
    private val advSearchViewModel by viewModels<AdvancedSearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = AdvancedSearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.advancedViewModel = advSearchViewModel

        binding.nextPage.setOnClickListener {
            view?.findNavController()?.navigate(
                AdvancedSearchFragmentDirections.actionAdvancedSearchFragmentToSearchResultsFragment()
            )
        }





        return binding.root
    }


}