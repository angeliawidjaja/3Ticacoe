package com.example.tictactoe.app.player

import androidx.databinding.Bindable
import androidx.databinding.Observable
import java.io.Serializable
import java.util.ArrayList

class PlayerItemModel: Serializable, Observable {
    var playerId: Int? = null
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    @Bindable
    var playerName: String = ""
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    @Bindable
    var playerScore: Int? = null
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}