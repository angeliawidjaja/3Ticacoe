package com.example.tictactoe.app.play

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.tictactoe.BR
import com.example.tictactoe.R
import com.example.tictactoe.app.player.PlayerItemModel
import java.io.Serializable

class PlayModel: Serializable, BaseObservable() {
    val FIRST_PLAYER = 1
    val SECOND_PLAYER = 0
    val GAME_DRAW = 2

    var firstPlayer: PlayerItemModel? = null
        @Bindable get() = field        // getter
        set(value) {         // setter
            field = value
            notifyPropertyChanged(BR.firstPlayer)
        }

    var secondPlayer: PlayerItemModel? = null
        @Bindable get() = field      // getter
        set(value) {         // setter
            field = value
            notifyPropertyChanged(BR.secondPlayer)
        }

    var roundCount: Int? = null
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.player1ColorStatus)
            notifyPropertyChanged(BR.player2ColorStatus)
            notifyPropertyChanged(BR.activePlayer)
        }

    @Bindable
    var activePlayer: Int? = null
        get(){
            return if(roundCount!!.rem(2) == 0) SECOND_PLAYER
            else FIRST_PLAYER
        }
        set(value) {
            field = value
        }

    var boardCells = arrayOf<Array<Int>>()
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    var isGameOver: Boolean = false
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    var winner: Int? = null

    @Bindable
    var gameResultMessage: String? = null
        get(){
            return if (winner!! == FIRST_PLAYER) firstPlayer!!.playerName + " Win! Play again?"
            else if (winner!! == SECOND_PLAYER) secondPlayer!!.playerName + " Win! Play again?"
            else if (winner!! == GAME_DRAW) "Game Draw! Play again?"
            else ""
        }

    var player1ColorStatus:Int? = null
        @Bindable get(){
            return if(isGameOver) R.color.colorGray
            else if (activePlayer == FIRST_PLAYER && roundCount != 10)
                R.color.colorBlue
            else
                R.color.colorGray
        }

    var player2ColorStatus:Int? = null
        @Bindable get() {
            return if(isGameOver) R.color.colorGray
            else if (activePlayer == SECOND_PLAYER && roundCount != 10) {
                R.color.colorRed
            } else
                R.color.colorGray
        }
}