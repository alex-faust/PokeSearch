package com.example.pokesearch.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.pokesearch.R
import com.example.pokesearch.databinding.SearchFragmentBinding
import com.example.pokesearch.utils.setQuery

class SearchFragment : Fragment() {

    val TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private var searchQuery = " name = "
    private var nameClicked = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchViewModel = searchViewModel

        val nameListForAdapter = resources.getStringArray(R.array.names_for_adapter)

        //TODO need to return back to this screen if you get a 404 screen, also give a reason why
        // and maybe put a progress bar


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nameListForAdapter )

        val nameList = binding.pokemonAutoText
        nameList.setAdapter(adapter)

        nameList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
           nameClicked = ""
           nameClicked = nameListForAdapter[position].replaceFirstChar { it.lowercase() }
            Log.i(TAG, "$nameClicked is clicked")
        }

        binding.searchToAdvanced.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToAdvanced()
            )
        }


        binding.goButton.setOnClickListener {
            setQuery(searchQuery + "\"$nameClicked\"")
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToResults()
            )

        }

        return binding.root
    }
}