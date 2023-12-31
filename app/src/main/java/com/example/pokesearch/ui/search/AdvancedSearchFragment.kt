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
import com.example.pokesearch.databinding.AdvancedSearchFragmentBinding
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.utils.setQuery

class AdvancedSearchFragment : Fragment() {

    private val TAG = "AdvancedSearchFragment"
    private lateinit var binding: AdvancedSearchFragmentBinding
    private val advSearchViewModel by viewModels<AdvancedSearchViewModel>()
    private var searchQuery = ArrayList<String>()
    private var type1Clicked = "NONE"
    private var type2Clicked = "NONE"
    private var abilityClicked = "NONE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = AdvancedSearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.advancedViewModel = advSearchViewModel

        val canvasView = CanvasFrame(requireContext())
        binding.advLayout.addView(canvasView)

        //code for types logic ---------------------------------------------------------------------
        val type1ListForAdapter = resources.getStringArray(R.array.type1_for_adapter)
        val adapter1: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            type1ListForAdapter)
        val type1List = binding.type1AutoText
        type1List.setAdapter(adapter1)
        type1List.onItemSelectedListener
        type1List.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            type1Clicked = ""
            type1Clicked = parent.getItemAtPosition(position).toString().lowercase()
        }

        val type2ListForAdapter = resources.getStringArray(R.array.type2_for_adapter)
        val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            type2ListForAdapter)
        val type2List = binding.type2AutoText
        type2List.setAdapter(adapter2)
        type2List.onItemSelectedListener
        type2List.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            type2Clicked = ""
            type2Clicked = parent.getItemAtPosition(position).toString().lowercase()
        }

        //code for abilities logic ---------------------------------------------------------------------
        val abilityListForAdapter = resources.getStringArray(R.array.abilities_for_adapter)
        val abilityAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.select_dialog_singlechoice_material,
            abilityListForAdapter)
        val abilityList = binding.abilityAutoText
        abilityList.setAdapter(abilityAdapter)
        abilityList.onItemSelectedListener
        abilityList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            abilityClicked = ""
            abilityClicked = parent.getItemAtPosition(position).toString().lowercase()
        }

        binding.advToSearchBtn.setOnClickListener {

            if (type1Clicked.equals("NONE") && type2Clicked.equals("NONE")) {
                //do nothing if none are chosen?
                //TODO maybe make a toast that says no type chosen or maybe evaluate the query with no type chosen
                Log.i(TAG, "outcome 1 $type1Clicked and $type2Clicked")
            } else if (type1Clicked.equals(type2Clicked)) {
                //if both selected are the same type
                searchQuery.add(" type1 = \"$type1Clicked\" ")
                Log.i(TAG, "outcome 2 $type1Clicked and $type2Clicked")
            } else if (type2Clicked.equals("NONE")) { //TODO fix the onlicklistener method because it keeps previous choice here. need to check if it's null or empty
                //do only type1 is chosen
                Log.i(TAG, "outcome 3 $type1Clicked and $type2Clicked")
                searchQuery.add(" type1 = \"$type1Clicked\" ")
            } else if (type1Clicked.equals("NONE") && !type2Clicked.equals("NONE")) {
                //if for some reason they choose a type for type 2 and nothing for type 1
                searchQuery.add(" type1 = \"$type2Clicked\" ")
                Log.i(TAG, "outcome 4 $type1Clicked and $type2Clicked")
            } else {
                //assumes both types are chosen, only other outcome I can think of right now
                //TODO need to check if they chose the reverse
                searchQuery.add(" type1 = \"$type1Clicked\" AND " +
                        "type2 = \"$type2Clicked\" OR " +
                        "type1 = \"$type2Clicked\" AND " +
                        "type2 = \"$type1Clicked\"")
                Log.i(TAG, "outcome 5 $type1Clicked and $type2Clicked")
            }

            //building the ability query
            if (!abilityClicked.equals("NONE")) {
                searchQuery.add(" ability1 = \"$abilityClicked\" " +
                        "OR ability2 = \"$abilityClicked\" " +
                        "OR ability3 = \"$abilityClicked\"")
            }

            //building the final query
            val size = searchQuery.size - 1
            val finalQuery = StringBuilder()
            if (size < 0) {
                finalQuery.append(" dexNum > 0") //.show all pokemon if there are no parameters chosen
            } else if (size == 0) { //means 1 item
                finalQuery.append(searchQuery[0])
            } else {
                //doing -1 because we dont want to add AND to the last query
                for (i in 0 until size) {
                    finalQuery.append(searchQuery[i] + "AND")
                }
                finalQuery.append(searchQuery[size])
            }

            setQuery(finalQuery.toString())
            view?.findNavController()?.navigate(
                AdvancedSearchFragmentDirections.actionAdvancedToSearchResults()
            )
        }
        return binding.root
    }
}