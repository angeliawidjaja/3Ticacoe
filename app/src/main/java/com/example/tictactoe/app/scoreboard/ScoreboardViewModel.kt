package com.example.tictactoe.app.scoreboard

import androidx.lifecycle.ViewModel
import com.example.tictactoe.db.Repository

class ScoreboardViewModel(repository: Repository) : ViewModel() {
    val scoreboardList = repository.players
}