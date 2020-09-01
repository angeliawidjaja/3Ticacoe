package com.example.tictactoe.app.scoreboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.PlayerViewModelFactory
import com.example.tictactoe.R
import com.example.tictactoe.Repository
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.databinding.ActivityScoreboardBinding
import com.example.tictactoe.db.TicTacToeDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreboardBinding
    private val scoreboardViewModel by viewModel<ScoreboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard)
        binding.viewModel = scoreboardViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvScoreboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        displayScoreboardList()
    }

    private fun displayScoreboardList(){
        scoreboardViewModel.scoreboardList.observe(this, Observer {
            binding.rvScoreboard.adapter = ScoreboardAdapter(it)
        })
    }
}