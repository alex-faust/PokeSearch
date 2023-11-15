package com.example.pokesearch.ui.game

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokesearch.R
import com.example.pokesearch.databinding.GameFragmentBinding
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.utils.createChannel
import com.example.pokesearch.utils.setQuery
import timber.log.Timber
import java.util.Random

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private val gameViewModel by viewModels<GameViewModel>()
    private lateinit var geoViewModel: GameViewModel
    private lateinit var thisContext: Context
    //private var pokemonName: String = ""
    //private val pokemonSprite : String = ""
    private var searchQuery = " name = "



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = GameFragmentBinding.inflate(inflater)

        binding.gameViewModel = gameViewModel
        geoViewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application,
                requireActivity()))[GameViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner


        createChannel(thisContext)

        val canvasView = CanvasFrame(thisContext)
        binding.gameLayout.addView(canvasView)




        val randomPokemon = resources.getStringArray(R.array.names_for_adapter)
        val random = Random(System.currentTimeMillis())

        binding.randomSelectBtn.setOnClickListener {
            val index = random.nextInt(randomPokemon.size-1)
            Timber.i(randomPokemon[index])
            setQuery("$searchQuery\"${randomPokemon[index].lowercase()}\"")
            geoViewModel.pokemonName = binding.randomPokemonName.toString()

            val id = findNavController().currentDestination?.id
            findNavController().popBackStack(id!!, true)
            findNavController().navigate(id)
        }




        binding.goToMapBtn.setOnClickListener {
            view?.findNavController()?.navigate(GameFragmentDirections.actionGameToMaps())
         }

        return binding.root
    }













    override fun onAttach(context: Context) {
        super.onAttach(context)
        thisContext = context
    }

}

/*
 // old way
    fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
        val intent = Intent(context, ReminderDescriptionActivity::class.java)
        intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
        return intent
    }


    // better way
    inline fun <reified T : Activity> Context.createIntent(vararg args: Pair<String, Any>) : Intent {
        val intent = Intent(this, T::class.java)
        intent.putExtras(bundleOf(*args))
        return intent
    }


    // usage
    fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
        return context.createIntent<ReminderDescriptionActivity>(EXTRA_ReminderDataItem to reminderDataItem)
    }
 */