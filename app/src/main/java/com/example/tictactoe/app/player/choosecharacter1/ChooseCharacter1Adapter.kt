package com.example.a3ticacoe.app.choosecharacter1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ListPlayerItemBinding

class ChooseCharacter1Adapter(private val playerList : List<PlayerDTO>, private val clickListener:(PlayerDTO)->Unit) : RecyclerView.Adapter<ChooseCharacter1ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCharacter1ViewHolder {
        val binding : ListPlayerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_player_item, parent,false)
        return ChooseCharacter1ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: ChooseCharacter1ViewHolder, position: Int) {
        //To display data on the list item
        holder.bind(playerList[position], clickListener)
    }
}

class ChooseCharacter1ViewHolder(val binding: ListPlayerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(player: PlayerDTO, clickListener:(PlayerDTO)->Unit){
        binding.tvPlayerName.text = player.playerName
        binding.cvPlayerList.setOnClickListener{
            //Pass the selected Player instance to the listItemClicked function
            clickListener(player)
        }
    }
}