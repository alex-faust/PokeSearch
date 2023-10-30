package com.example.pokesearch.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokesearch.databinding.PokemonRvItemBinding
import com.example.pokesearch.model.Pokemon

class PokemonRVAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Pokemon, PokemonRVAdapter.PokemonViewHolder>(DiffCallBack) {
        class PokemonViewHolder(private var binding: PokemonRvItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(pokemon: Pokemon) {
                binding.pokemonItems = pokemon
                binding.executePendingBindings()
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): PokemonViewHolder {
        return PokemonViewHolder(PokemonRvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonItems = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemonItems)
        }
        holder.bind(pokemonItems)
    }

    class OnClickListener(val clickListener: (pokemon: Pokemon) -> Unit) {
        fun onClick(pokemon: Pokemon) = clickListener(pokemon)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }
    }
}