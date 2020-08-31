package com.example.tictactoe.app.player.choosecharacter2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.PlayerViewModelFactory
import com.example.tictactoe.R
import com.example.tictactoe.Repository
import com.example.a3ticacoe.app.choosecharacter2.ChooseCharacter2Adapter
import com.example.tictactoe.databinding.ActivityChooseCharacter2Binding
import com.example.tictactoe.app.play.PlayActivity
import com.example.tictactoe.db.TicTacToeDatabase
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.app.play.PlayModel

class ChooseCharacter2Activity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChooseCharacter2Binding
    private lateinit var chooseCharacter2ViewModel: ChooseCharacter2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_character2)
        initViewModel()
        binding.lifecycleOwner = this
        initListener()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvExistingPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        displayPlayersList() //Function to observe list of players data in the database table
    }

    private fun initViewModel(){
        //Init DAO instance
        val playerDao = TicTacToeDatabase.getInstance(application).playerDAO
        //Init repo instance using DAO instance
        val repository = Repository(playerDao)
        //Init VM Factory instance using Repo instance
        val factory = PlayerViewModelFactory(repository, getPlayModelFromIntent())
        //Init ChooseCharacter2 VM instance
        chooseCharacter2ViewModel = ViewModelProvider(this, factory).get(ChooseCharacter2ViewModel::class.java)
        //Assign VM instance into data binding object
        binding.viewModel = chooseCharacter2ViewModel
    }

    private fun getPlayModelFromIntent(): PlayModel {
        return intent.getSerializableExtra("gameModel") as PlayModel
    }

    private fun initListener(){
        binding.btnSubmitPlayer2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.btnSubmitPlayer2){
            if(chooseCharacter2ViewModel.validPlayerName()){
                intentToGame()
            }
        }
    }

    private fun displayPlayersList(){
        chooseCharacter2ViewModel.players?.observe(this, Observer {
            binding.rvExistingPlayer.adapter =
                ChooseCharacter2Adapter(it) { selectedItem: PlayerDTO ->
                    listItemClicked(selectedItem)
                }
        })
    }

    private fun listItemClicked(player: PlayerDTO){
        chooseCharacter2ViewModel.updateExistingPlayer(player)
        intentToGame()
    }

    private fun intentToGame(){
        intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("gameModel", chooseCharacter2ViewModel.getGameModel())
        startActivity(intent)
    }
}