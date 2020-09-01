package com.example.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.app.player.choosecharacter1.ChooseCharacter1ViewModel
import com.example.tictactoe.app.player.choosecharacter2.ChooseCharacter2ViewModel
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.app.play.PlayViewModel
import com.example.tictactoe.app.scoreboard.ScoreboardViewModel
import java.lang.IllegalArgumentException

class PlayerViewModelFactory(private val repository : Repository, private val playModel: PlayModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreboardViewModel::class.java)){
            return ScoreboardViewModel(
                repository
            ) as T
        }
        else if(modelClass.isAssignableFrom(ChooseCharacter2ViewModel::class.java)){
            return ChooseCharacter2ViewModel(
                repository, playModel
            ) as T
        }
        else if(modelClass.isAssignableFrom(PlayViewModel::class.java)){
            return PlayViewModel(
                repository, playModel
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}