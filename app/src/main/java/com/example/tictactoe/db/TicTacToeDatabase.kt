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

    companion object{
        @Volatile
        private var INSTANCE : TicTacToeDatabase? = null
        fun getInstance(context : Context): TicTacToeDatabase {
            synchronized(this){
                var instance =
                    INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TicTacToeDatabase::class.java,
                        "tictactoe_database").build()
                }
                return instance
            }
        }
    }
}