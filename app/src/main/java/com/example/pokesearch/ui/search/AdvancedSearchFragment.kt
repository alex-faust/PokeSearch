package com.example.pokesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.pokesearch.databinding.AdvancedSearchFragmentBinding
import com.example.pokesearch.utils.setPokemonSearchedQuery

class AdvancedSearchFragment: Fragment() {

    private lateinit var binding: AdvancedSearchFragmentBinding
    private val advSearchViewModel by viewModels<AdvancedSearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = AdvancedSearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.advancedViewModel = advSearchViewModel

        val query = SimpleSQLiteQuery(
            "SELECT * FROM pokemondatabase WHERE type1 = fire OR type2 = fire")

        //setPokemonSearchedQuery(query)

        val emptyString = ""
        binding.nextPage.setOnClickListener {
            //setPokemonSearchedQuery(query)
            view?.findNavController()?.navigate(
                AdvancedSearchFragmentDirections.actionAdvancedToSearchResults(emptyString)
            )
        }






        return binding.root
    }


}

//TODO() notes
/**
 * Make sure when adding fragment, make a counter so that the same value can't be added
 *          more than once and no more than 6
 *
 */