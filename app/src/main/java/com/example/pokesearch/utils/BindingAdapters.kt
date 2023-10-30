package com.example.pokesearch.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokesearch.model.Pokemon
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