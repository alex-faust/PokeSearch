package com.example.pokesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pokesearch.databinding.SearchFragmentBinding
import com.example.pokesearch.utils.setPokemonSearchedName

class SearchFragment : Fragment() {

    val TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding

    //private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var pokemonNameToRetrieve: String

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchViewModel = searchViewModel

        //TODO need to return back to this screen if you get a 404 screen, also give a reason why
        // and maybe put a progress bar

        binding.searchToAdvanced.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToAdvanced()
            )
        }
        binding.goButton.setOnClickListener {
            pokemonNameToRetrieve = binding.pokemonToSearch.text.toString()
            setPokemonSearchedName(pokemonNameToRetrieve)
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToResults(pokemonNameRetrieved = pokemonNameToRetrieve)
            )

        }

        return binding.root
    }
}