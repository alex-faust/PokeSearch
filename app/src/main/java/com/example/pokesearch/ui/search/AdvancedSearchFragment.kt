package com.example.pokesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.pokesearch.databinding.AdvancedSearchFragmentBinding

class AdvancedSearchFragment: Fragment() {

    private lateinit var binding: AdvancedSearchFragmentBinding
    private val advSearchViewModel by viewModels<AdvancedSearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = AdvancedSearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.advancedViewModel = advSearchViewModel

        val emptyString = ""

        binding.nextPage.setOnClickListener {
            view?.findNavController()?.navigate(
                AdvancedSearchFragmentDirections.actionAdvancedToSearchResults(emptyString)
            )
        }





        return binding.root
    }


}