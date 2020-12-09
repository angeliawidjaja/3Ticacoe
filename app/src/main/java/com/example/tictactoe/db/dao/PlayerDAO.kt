package com.example.tictactoe.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a3ticacoe.db.entities.PlayerDTO

@Dao
interface PlayerDAO {
    @Insert
    suspend fun insertNewPlayer(player: PlayerDTO)

    @Query("SELECT * FROM player_table")
    fun getAllPlayers() : LiveData<List<PlayerDTO>>

    @Query("SELECT * FROM player_table WHERE player_id != :playerId")
    fun getSectionTwoPlayers(playerId: Int) : LiveData<List<PlayerDTO>>

    @Query("UPDATE player_table SET player_score = player_score + :playerScore WHERE player_id = :playerId")
    fun updateScoreData(playerScore: Int, playerId: Int)
}