package com.example.tictactoe.di

import android.app.Application
import com.example.tictactoe.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(
                databaseModule,
                repositoryModule,
                playModelModule,
                chooseCharacter1viewModelModule,
                chooseCharacter2ViewModelModule,
                scoreboardViewModelModule,
                playViewModelModule
            ))
        }
    }
}