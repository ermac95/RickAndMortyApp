package com.mycodeflow.rickandmortycharsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem
import com.mycodeflow.rickandmortycharsapp.databinding.CharViewHolderBinding
import com.mycodeflow.rickandmortycharsapp.presentation.ui.CharsListFragmentDirections


class MainMenuCharsListAdapter: RecyclerView.Adapter<CharViewHolder>() {

    private var chars: List<CharItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = CharViewHolderBinding.inflate(inflater, parent, false)
        return CharViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharViewHolder, position: Int) {
        holder.bind(chars[position])
        holder.itemView.setOnClickListener {
            val action = CharsListFragmentDirections.actionToDetails(chars[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int = chars.size

    fun setData(updatedChars: List<CharItem>){
        chars = updatedChars
        notifyDataSetChanged()
    }
}

class CharViewHolder(
        private val itemBinding: CharViewHolderBinding
) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(char: CharItem) = with(itemBinding){
        Glide.with(itemView)
                .load(char.image)
                .into(portrait)
        name.text = char.name
        status.text = "Status: ${char.status}"
    }
}
