package com.example.tictactoe.app.player.choosecharacter2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.R
import com.example.a3ticacoe.app.choosecharacter2.ChooseCharacter2Adapter
import com.example.tictactoe.databinding.ActivityChooseCharacter2Binding
import com.example.tictactoe.app.play.PlayActivity
import com.example.a3ticacoe.db.entities.PlayerDTO
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseCharacter2Activity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChooseCharacter2Binding
    private val chooseCharacter2ViewModel by viewModel<ChooseCharacter2ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_character2)
        binding.viewModel = chooseCharacter2ViewModel
        binding.lifecycleOwner = this
        initListener()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvExistingPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        displayPlayersList() //Function to observe list of players data in the database table
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
        startActivity(intent)
    }
}