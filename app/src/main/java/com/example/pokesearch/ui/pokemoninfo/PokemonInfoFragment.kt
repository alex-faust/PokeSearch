package com.example.pokesearch.ui.pokemoninfo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokesearch.databinding.PokemonInfoFragmentBinding
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.ui.StatBarState
import timber.log.Timber

class PokemonInfoFragment : Fragment() {

    private lateinit var binding: PokemonInfoFragmentBinding
    private val pokeViewModel by viewModels<PokemonInfoViewModel>()
    lateinit var square: ImageView

    private var hpValueAnimator = ValueAnimator()
    private var atkValueAnimator = ValueAnimator()
    private var defValueAnimator = ValueAnimator()
    private var spAtkValueAnimator = ValueAnimator()
    private var spDefValueAnimator = ValueAnimator()
    private var spdValueAnimator = ValueAnimator()
    private val duration: Long = 5000
    private val extraBoost = 40

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PokemonInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val canvasView = CanvasFrame(requireContext())
        binding.infoLayout.addView(canvasView)

        val selectedPokemon = PokemonInfoFragmentArgs.fromBundle(requireArguments()).selectedPokemon
        binding.pokemonInfo = selectedPokemon

        hpValueAnimator.duration = duration
        val hpFloat = selectedPokemon.stats.hp
        hpValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + hpFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.hpTrackBtn.translationX = value
            }
        }
        hpValueAnimator.start()

        atkValueAnimator.duration = duration
        val atkFloat = selectedPokemon.stats.attack
        atkValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + atkFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.atkTrackBtn.translationX = value
            }
        }
        atkValueAnimator.start()

        defValueAnimator.duration = duration
        val defFloat = selectedPokemon.stats.defense
        defValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + defFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.defTrackBtn.translationX = value
            }
        }
        defValueAnimator.start()

        spAtkValueAnimator.duration = duration
        val spAtkFloat = selectedPokemon.stats.specialAttack
        spAtkValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + spAtkFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.spAtkTrackBtn.translationX = value
            }
        }
        spAtkValueAnimator.start()

        spDefValueAnimator.duration = duration
        val spDefFloat = selectedPokemon.stats.specialDefense
        spDefValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + spDefFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.spDefTrackBtn.translationX = value
            }
        }
        spDefValueAnimator.start()

        spdValueAnimator.duration = duration
        val spdFloat = selectedPokemon.stats.speed
        spdValueAnimator = ValueAnimator.ofFloat(0f, (extraBoost + spdFloat).toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.spdTrackBtn.translationX = value
            }
        }
        spdValueAnimator.start()

        //TODO highest pokemon stat is 255 (for the animation end)
        //TODO style has too much black



        return binding.root
    }
}