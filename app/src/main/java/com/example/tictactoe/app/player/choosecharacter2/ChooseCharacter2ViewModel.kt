package com.example.tictactoe.app.player.choosecharacter2

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

class ChooseCharacter2ViewModel(private val repository: Repository, private var playModel: PlayModel) : ViewModel(), Observable {
    val players = playModel.firstPlayer?.playerId?.let { repository.getPlayersSectionTwo(it) }

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val invalidNotif = MutableLiveData<String>()

    private var isExist = false
    private lateinit var currentPlayer : PlayerDTO
    private var secondPlayer: PlayerItemModel = PlayerItemModel()

    fun submitData(){
        val name = inputName.value!!
        var id = players?.value!!.size + 2
        currentPlayer = PlayerDTO(id, name, 0)
        setPlayerTwo(currentPlayer)
        playModel.secondPlayer = secondPlayer
        insertNewPlayer(currentPlayer)
        inputName.value = null
    }

    fun insertNewPlayer(player: PlayerDTO): Job = viewModelScope.launch {
        repository.insert(player)
    }

    fun updateExistingPlayer(player: PlayerDTO){
        setPlayerTwo(player)
        playModel.secondPlayer = secondPlayer
        inputName.value = player.playerName
    }

    fun setPlayerTwo(player: PlayerDTO){
        secondPlayer.playerId = player.playerId
        secondPlayer.playerName = player.playerName
        secondPlayer.playerScore = player.playerScore
    }

    fun validPlayerName(): Boolean {
        if(inputName.value.isNullOrEmpty()){
            invalidNotif.value = "Player name should not be empty!"
            return false
        }
        else if(inputName.value.equals(playModel.firstPlayer!!.playerName)){
            //Validasi kalau iseng input nama yg sama
            invalidNotif.value = "Player Name cannot be the same as the previous player"
            return false
        }
        else{
            if (players != null) {
                for (player in players.value!!){
                    if(inputName.value.equals(player.playerName)){
                        isExist = true
                        currentPlayer = player
                        break
                    }
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