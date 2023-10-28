package com.example.pokesearch.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("spriteUrl")
fun bindPokemonSprite(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(imgUri).into(imgView)
    }
}