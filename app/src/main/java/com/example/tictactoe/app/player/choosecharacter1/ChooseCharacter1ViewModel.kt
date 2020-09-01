package com.example.tictactoe.app.player.choosecharacter1

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3ticacoe.db.entities.PlayerDTO
import com.example.tictactoe.db.Repository
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.app.player.PlayerItemModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable

class ChooseCharacter1ViewModel(private val repository: Repository, private var playModel: PlayModel) : ViewModel(), Observable, Serializable {
    var players = repository.players

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val invalidNotif = MutableLiveData<String>()

    private var isExist = false
    private lateinit var currentPlayer : PlayerDTO
    private var firstPlayer: PlayerItemModel = PlayerItemModel()

    fun submitData(){
        val name = inputName.value!!
        var id = players.value!!.size + 1
        currentPlayer = PlayerDTO(id, name, 0)
        setPlayerOne(currentPlayer)
        playModel.firstPlayer = firstPlayer
        insertNewPlayer(currentPlayer)
        inputName.value = null
    }

    private fun insertNewPlayer(player: PlayerDTO): Job = viewModelScope.launch {
            repository.insert(player)
        }

    fun updateExistingPlayer(player: PlayerDTO){
        setPlayerOne(player)
        playModel.firstPlayer = firstPlayer
        inputName.value = player.playerName
        invalidNotif.value = null
    }

    fun setPlayerOne(player: PlayerDTO){
        firstPlayer.playerId = player.playerId
        firstPlayer.playerName = player.playerName
        firstPlayer.playerScore = 0
    }

    fun validPlayerName(): Boolean {
        if(inputName.value.isNullOrEmpty()){
            invalidNotif.value = "Player name should not be empty!"
            return false
        }
        else{
            for (player in players.value!!){
                if(inputName.value.equals(player.playerName)){
                    isExist = true
                    currentPlayer = player
                    break
                }
            }

            //Cek data bdk status
            if(!isExist){
                //Kalo baru datanya
                submitData()
            }

            inputName.value = null
            invalidNotif.value = null
            isExist = false

            return true
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}