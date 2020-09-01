package com.example.tictactoe.app.player.choosecharacter1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a3ticacoe.app.choosecharacter1.ChooseCharacter1Adapter
import com.example.tictactoe.app.player.choosecharacter2.ChooseCharacter2Activity
import com.example.tictactoe.db.TicTacToeDatabase
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.*
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.databinding.ActivityChooseCharacter1Binding
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ChooseCharacter1Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChooseCharacter1Binding
    private val chooseCharacter1ViewModel by viewModel<ChooseCharacter1ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_character1)
        binding.lifecycleOwner = this
        binding.viewModel = chooseCharacter1ViewModel
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
        startActivity(intent)
    }
}