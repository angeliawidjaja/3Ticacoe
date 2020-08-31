package com.example.tictactoe.app.scoreboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ListScoreboardItemBinding

class ScoreboardAdapter (private val playerList : List<PlayerDTO>) : RecyclerView.Adapter<ScoreboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardViewHolder {
        val binding : ListScoreboardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_scoreboard_item, parent,false)
        return ScoreboardViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: ScoreboardViewHolder, position: Int) {
        holder.bind(playerList[position])
    }
}

class ScoreboardViewHolder(val binding: ListScoreboardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(player: PlayerDTO){
        binding.tvPlayerName.text = player.playerName
        binding.tvPlayerScore.text = player.playerScore.toString()
    }
}