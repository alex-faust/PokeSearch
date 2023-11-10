package com.example.pokesearch.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.model.PokemonTypes
import com.squareup.picasso.Picasso


@BindingAdapter("spriteUrl")
fun bindPokemonSprite(imgView: ImageView, imgUrl: String?){

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(imgUri).into(imgView)
    }
}

@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data:List<Pokemon>?) {
    val adapter = recyclerView.adapter as PokemonRVAdapter
    adapter.submitList(data)
}

@BindingAdapter("types")
fun bindTypes(imgView: ImageView, type: String?) {
    when (type) {
        PokemonTypes.BUG.type -> imgView.setImageResource(PokemonTypes.BUG.drawable)
        PokemonTypes.DARK.type -> imgView.setImageResource(PokemonTypes.DARK.drawable)
        PokemonTypes.DRAGON.type -> imgView.setImageResource(PokemonTypes.DRAGON.drawable)
        PokemonTypes.ELECTRIC.type -> imgView.setImageResource(PokemonTypes.ELECTRIC.drawable)
        PokemonTypes.FAIRY.type -> imgView.setImageResource(PokemonTypes.FAIRY.drawable)
        PokemonTypes.FIGHTING.type -> imgView.setImageResource(PokemonTypes.FIGHTING.drawable)
        PokemonTypes.FIRE.type -> imgView.setImageResource(PokemonTypes.FIRE.drawable)
        PokemonTypes.FLYING.type -> imgView.setImageResource(PokemonTypes.FLYING.drawable)
        PokemonTypes.GHOST.type -> imgView.setImageResource(PokemonTypes.GHOST.drawable)
        PokemonTypes.GRASS.type -> imgView.setImageResource(PokemonTypes.GRASS.drawable)
        PokemonTypes.GROUND.type -> imgView.setImageResource(PokemonTypes.GROUND.drawable)
        PokemonTypes.ICE.type -> imgView.setImageResource(PokemonTypes.ICE.drawable)
        PokemonTypes.NORMAL.type -> imgView.setImageResource(PokemonTypes.NORMAL.drawable)
        PokemonTypes.POISON.type -> imgView.setImageResource(PokemonTypes.POISON.drawable)
        PokemonTypes.PSYCHIC.type -> imgView.setImageResource(PokemonTypes.PSYCHIC.drawable)
        PokemonTypes.ROCK.type -> imgView.setImageResource(PokemonTypes.ROCK.drawable)
        PokemonTypes.STEEL.type -> imgView.setImageResource(PokemonTypes.STEEL.drawable)
        PokemonTypes.WATER.type -> imgView.setImageResource(PokemonTypes.WATER.drawable)
        else -> imgView.visibility = View.GONE
    }
}



