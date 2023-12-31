package com.example.pokesearch.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.pokesearch.R
import com.example.pokesearch.databinding.SearchFragmentBinding
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.utils.createChannel
import com.example.pokesearch.utils.setQuery
import com.example.pokesearch.work.PokemonWork
import timber.log.Timber
import java.util.UUID


class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private var searchQuery = " name = "
    private var nameClicked = "NONE"
    //private val myPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.searchViewModel = searchViewModel

        createChannel(requireContext())

        val canvasView = CanvasFrame(requireContext())
        binding.searchLayout.addView(canvasView)

        /*
        val myPrefs: SharedPreferences = requireActivity().getSharedPreferences("prefID", Context.MODE_PRIVATE)
        val workID = myPrefs.getString("prefID", "num")
        val myWork: WorkManager = WorkManager.getInstance(requireContext())

        myWork.getWorkInfoById(workID as UUID)
        Timber.i("the work is: $myWork")
        */

        val nameListForAdapter = resources.getStringArray(R.array.names_for_adapter)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nameListForAdapter )
        val nameList = binding.pokemonAutoText
        nameList.setAdapter(adapter)
        nameList.onItemSelectedListener
        nameList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
           nameClicked = ""
           nameClicked = parent.getItemAtPosition(position).toString().lowercase()
            Timber.tag(TAG).i("$nameClicked is clicked")
        }

        binding.searchToAdvanced.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToAdvanced()
            )
        }

        binding.goButton.setOnClickListener {
            if (nameClicked.equals("NONE")) {
                setQuery(" dexNum > 0")
            } else setQuery(searchQuery + "\"$nameClicked\"")
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToResults()
            )
        }

        binding.playGameBtn.setOnClickListener {
            view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchToGame()
            )
        }
        return binding.root
    }

    fun isSpinning(spin: Boolean) {
        if (spin) {
            //binding.pokeMotion.pokeballMotion.scene.
        }
    }

}