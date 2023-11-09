package com.example.pokesearch.ui.pokemoninfo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokesearch.databinding.PokemonInfoFragmentBinding
import com.example.pokesearch.ui.CanvasFrame

class PokemonInfoFragment : Fragment() {

    private val TAG = "PokemonInfo"
    private lateinit var binding: PokemonInfoFragmentBinding
    private val pokeViewModel by viewModels<PokemonInfoViewModel>()
    lateinit var square: ImageView
    lateinit var hpNum: String
    lateinit var atkNum: String
    lateinit var defNum: String
    lateinit var spAtkNum: String
    lateinit var spDefNum: String
    lateinit var spdNum: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PokemonInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val canvasView = CanvasFrame(requireContext())
        binding.infoLayout.addView(canvasView)

        val selectedPokemon = PokemonInfoFragmentArgs.fromBundle(requireArguments()).selectedPokemon
        binding.pokemonInfo = selectedPokemon
        square = binding.hpBar
        square.pivotX = 0f
        hpNum = selectedPokemon.stats.hp
        
        scaler()

        //TODO highest pokemon stat is 255 (for the animation end)


        return binding.root
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, hpNum.toFloat())
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)
        val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            square, scaleX, scaleY)
        animator.duration = 5000
        animator.start()

    }
    
    
    
    
}