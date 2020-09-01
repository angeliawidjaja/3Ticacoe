package com.example.tictactoe.db

import androidx.lifecycle.LiveData
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.db.dao.PlayerDAO

class Repository(private val playerDAO: PlayerDAO) {
    val players = playerDAO.getAllPlayers()

    fun getPlayersSectionTwo(playerId: Int): LiveData<List<PlayerDTO>> {
        return playerDAO.getSectionTwoPlayers(playerId)
    }

    suspend fun insert(player: PlayerDTO){
        playerDAO.insertNewPlayer(player)
    }

    suspend fun updateWinnerScore(winnerScore:Int, winnerId: Int){
        playerDAO.updateWinnerData(winnerScore, winnerId)
    }

    suspend fun updateLoserScore(loserScore:Int, loserId: Int){
        playerDAO.updateWinnerData(loserScore, loserId)
    }
}