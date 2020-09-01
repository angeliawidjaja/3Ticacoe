package com.example.tictactoe.app.play

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.R
import com.example.tictactoe.app.main.MainActivity
import com.example.tictactoe.databinding.ActivityPlayBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPlayBinding
    private val playViewModel by viewModel<PlayViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)
        binding.lifecycleOwner = this
        initListener()
        binding.model = playViewModel.getPlayModel()
    }

    private fun initListener(){
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v == v as Button){
                if(v.isEnabled){
                    var rowId = -1
                    var columnId = -1

                    when(v.id){
                        binding.button1.id -> {rowId = 0; columnId = 0}
                        binding.button2.id -> {rowId = 0; columnId = 1}
                        binding.button3.id -> {rowId = 0; columnId = 2}
                        binding.button4.id -> {rowId = 1; columnId = 0}
                        binding.button5.id -> {rowId = 1; columnId = 1}
                        binding.button6.id -> {rowId = 1; columnId = 2}
                        binding.button7.id -> {rowId = 2; columnId = 0}
                        binding.button8.id -> {rowId = 2; columnId = 1}
                        binding.button9.id -> {rowId = 2; columnId = 2}

                    }

                    v.setBackgroundColor(ContextCompat.getColor(this, playViewModel.handleButtonColor()))
                    v.isEnabled = false
                    playViewModel.handleGameRole(rowId, columnId)
                    if(playViewModel.getPlayModel().isGameOver){
                        Toast.makeText(this, playViewModel.getPlayModel().firstPlayer!!.playerName + ": " + playViewModel.getPlayModel().firstPlayer!!.playerScore.toString() + " " + playViewModel.getPlayModel().secondPlayer!!.playerName + ": " + playViewModel.getPlayModel().secondPlayer!!.playerScore.toString(), Toast.LENGTH_LONG).show()
                        createPlayAgainAlertDialog()
                    }
                }
            }
        }
    }

    private fun intentToMainScreen(){
        intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        createExitGameAlertDialog()
    }

    private fun createPlayAgainAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Result")
        builder.setMessage(playViewModel.getPlayModel().gameResultMessage)
        builder.setPositiveButton("Yes") {_,_ -> playViewModel.resetGame(); resetButtonColor(); resetButtonenableability()}
        builder.setNegativeButton("No") {_,_ -> playViewModel.resetGame(); intentToMainScreen()}
        val alertDialog: AlertDialog = builder.show()
        alertDialog.setCanceledOnTouchOutside(false)
    }

    private fun createExitGameAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Stop The Game")
        builder.setMessage("Are you sure want to stop the game? Your current data won't be saved!")
        builder.setPositiveButton("Yes") {_,_ -> playViewModel.resetGame(); intentToMainScreen()}
        builder.setNegativeButton("No") {_,_ -> }
        val alertDialog: AlertDialog = builder.show()
        alertDialog.setCanceledOnTouchOutside(false)
    }

    private fun resetButtonColor(){
        binding.button1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button5.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button7.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button8.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
        binding.button9.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayBase))
    }

    private fun resetButtonenableability(){
        binding.button1.isEnabled = true
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
    }
}