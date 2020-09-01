package com.example.tictactoe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tictactoe.db.dao.PlayerDAO
import com.example.a3ticacoe.db.entities.PlayerDTO

@Database(entities = [PlayerDTO::class], version = 1, exportSchema = false)
abstract class TicTacToeDatabase : RoomDatabase() {
    abstract val playerDAO : PlayerDAO
}