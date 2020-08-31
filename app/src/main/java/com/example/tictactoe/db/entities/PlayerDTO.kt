package com.example.a3ticacoe.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class PlayerDTO(
    @PrimaryKey @ColumnInfo(name = "player_id")var playerId:Int,
    @ColumnInfo(name = "player_name") var playerName: String,
    @ColumnInfo(name = "player_score") var playerScore: Int
)