package com.example.tictactoe.app.play

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.R
import com.example.tictactoe.db.Repository
import com.example.tictactoe.app.player.PlayerItemModel
import kotlinx.coroutines.launch

class PlayViewModel(private val repository: Repository, private val playModel: PlayModel): ViewModel() {
    init {
        playModel.roundCount = 1
        initBoardCells()
    }

    private fun initBoardCells(){
        for (i in 0..2) {
            var array = arrayOf<Int>()
            for (j in 0..2) {
                array += -1
            }
            playModel.boardCells += array
        }
    }

    fun getPlayModel(): PlayModel {
        return playModel
    }

    fun handleButtonColor(): Int {
        return if(playModel.activePlayer == playModel.SECOND_PLAYER) R.color.colorRed
        else R.color.colorBlue
    }

    private fun moveToNextRound(){
        playModel.roundCount = playModel.roundCount?.plus(1)
    }

    fun handleGameRole(rowId: Int, columnId: Int) {
        if(playModel.activePlayer == playModel.SECOND_PLAYER){
            playModel.boardCells[rowId][columnId] = playModel.SECOND_PLAYER
        }
        else{
            playModel.boardCells[rowId][columnId] = playModel.FIRST_PLAYER
        }
        validateGameBoard()
    }

    private fun validateGameBoard(){
        if(!playModel.isGameOver){
            for(rowId in 0..2) {
                if(playModel.boardCells[rowId][0] == playModel.boardCells[rowId][1] &&
                    playModel.boardCells[rowId][1] == playModel.boardCells[rowId][2]){
                    if(playModel.boardCells[rowId][0] == playModel.FIRST_PLAYER){
                        handlePlayer1Win()
                        break;
                    }
                    if(playModel.boardCells[rowId][0] == playModel.SECOND_PLAYER){
                        handlePlayer2Win()
                        break;
                    }
                }
            }
        }

        if(!playModel.isGameOver){
            for(columnId in 0..2){
                if(playModel.boardCells[0][columnId] == playModel.boardCells[1][columnId] &&
                    playModel.boardCells[1][columnId] == playModel.boardCells[2][columnId]){
                    if(playModel.boardCells[0][columnId] == playModel.FIRST_PLAYER){
                        handlePlayer1Win()
                        break;
                    }
                    if(playModel.boardCells[0][columnId] == playModel.SECOND_PLAYER){
                        handlePlayer2Win()
                        break;
                    }
                }
            }
        }

        if(!playModel.isGameOver){
            if((playModel.boardCells[0][0] == playModel.boardCells[1][1] && playModel.boardCells[1][1] == playModel.boardCells[2][2]) ||
                (playModel.boardCells[0][2] == playModel.boardCells[1][1] && playModel.boardCells[1][1] == playModel.boardCells[2][0])){
                if(playModel.boardCells[1][1] == playModel.FIRST_PLAYER){
                    handlePlayer1Win()
                }
                if(playModel.boardCells[1][1] == playModel.SECOND_PLAYER){
                    handlePlayer2Win()
                }
            }
        }

        if(!playModel.isGameOver){
            if(playModel.roundCount == 9){
                handleGameDraw()
            }
            else{
                moveToNextRound()
            }
        }
    }

    private fun handlePlayer1Win(){
        playModel.firstPlayer!!.playerScore = playModel.firstPlayer!!.playerScore?.plus(10)
        playModel.secondPlayer!!.playerScore = playModel.secondPlayer!!.playerScore?.minus(10)
        updateScore(playModel.firstPlayer!!, playModel.secondPlayer!!)
        playModel.isGameOver = true
        playModel.winner = playModel.FIRST_PLAYER
    }

    private fun handlePlayer2Win(){
        playModel.secondPlayer!!.playerScore = playModel.secondPlayer!!.playerScore?.plus(10)
        playModel.firstPlayer!!.playerScore = playModel.firstPlayer!!.playerScore?.minus(10)
        updateScore(playModel.secondPlayer!!, playModel.firstPlayer!!)
        playModel.isGameOver = true
        playModel.winner = playModel.SECOND_PLAYER
    }

    private fun handleGameDraw(){
        playModel.isGameOver = true
        playModel.winner = playModel.GAME_DRAW
    }

    private fun updateScore(winnerPlayer: PlayerItemModel, loserPlayer: PlayerItemModel){
        viewModelScope.launch {
            repository.updateWinnerScore(winnerPlayer.playerScore!!, winnerPlayer.playerId!!)
            repository.updateLoserScore(loserPlayer.playerScore!!, loserPlayer.playerId!!)
        }
    }

    fun resetGame(){
        playModel.isGameOver = false
        playModel.roundCount = 1
        playModel.winner = null
        playModel.gameResultMessage = null
        playModel.firstPlayer!!.playerScore = 0
        playModel.secondPlayer!!.playerScore = 0
        resetBoardCells()
    }

    private fun resetBoardCells(){
        for (i in 0..2) {
            for (j in 0..2) {
                playModel.boardCells[i][j] = -1
            }
        }
    }
}