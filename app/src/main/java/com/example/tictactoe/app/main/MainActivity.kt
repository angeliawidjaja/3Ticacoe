package com.example.tictactoe.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tictactoe.R
import com.example.tictactoe.app.player.choosecharacter1.ChooseCharacter1Activity
import com.example.tictactoe.app.scoreboard.ScoreboardActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener(){
        btn_play.setOnClickListener(this)
        btn_scoreboard.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == btn_play){
            startActivity(Intent(this, ChooseCharacter1Activity::class.java))
        }
        else if(v == btn_scoreboard){
            startActivity(Intent(this, ScoreboardActivity::class.java))
        }
    }
}