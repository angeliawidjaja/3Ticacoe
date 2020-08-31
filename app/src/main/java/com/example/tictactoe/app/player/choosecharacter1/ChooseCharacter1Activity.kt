package com.example.tictactoe.app.player.choosecharacter1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.PlayerViewModelFactory
import com.example.tictactoe.Repository
import com.example.a3ticacoe.app.choosecharacter1.ChooseCharacter1Adapter
import com.example.tictactoe.app.player.choosecharacter2.ChooseCharacter2Activity
import com.example.tictactoe.db.TicTacToeDatabase
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.R
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.databinding.ActivityChooseCharacter1Binding

class ChooseCharacter1Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChooseCharacter1Binding
    private lateinit var chooseCharacter1ViewModel: ChooseCharacter1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_character1)
        initViewModel()
        //Providing Lifecycle owner => Live Data for Data Binding
        binding.lifecycleOwner = this
        initListener()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        displayPlayersList()
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
        val factory = PlayerViewModelFactory(repository, PlayModel())
        //Init ChooseCharacter1 VM instance
        chooseCharacter1ViewModel = ViewModelProvider(this, factory).get(ChooseCharacter1ViewModel::class.java)
        //Assign VM instance into data binding object
        binding.viewModel = chooseCharacter1ViewModel
    }

    private fun initListener(){
        binding.btnSubmitPlayer1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.btnSubmitPlayer1){
            if(chooseCharacter1ViewModel.validPlayerName()){
                intentToChooseCharacter2()
            }
        }
    }

    private fun displayPlayersList(){
        //Asal Mula: PlayerDAO (getAllPlayers() => LiveData) -> Repository (players) -> VM (players)
        chooseCharacter1ViewModel.players.observe(this, Observer {
            //Using Kotlin higher-order function and lambda expression to pass listItemClicked function as an argument
            binding.rvExistingPlayer.adapter =
                ChooseCharacter1Adapter(it) { selectedItem: PlayerDTO ->
                    listItemClicked(selectedItem)
                }
        })
    }

    private fun listItemClicked(player: PlayerDTO){
        chooseCharacter1ViewModel.updateExistingPlayer(player)
        intentToChooseCharacter2()
    }

    private fun intentToChooseCharacter2(){
        intent = Intent(this, ChooseCharacter2Activity::class.java)
        intent.putExtra("gameModel", chooseCharacter1ViewModel.getGameModel())
        startActivity(intent)
    }
}