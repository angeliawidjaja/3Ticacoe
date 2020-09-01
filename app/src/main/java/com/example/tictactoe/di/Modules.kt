package com.example.tictactoe.di

import android.app.Application
import androidx.room.Room
import com.example.tictactoe.db.Repository
import com.example.tictactoe.app.play.PlayModel
import com.example.tictactoe.app.play.PlayViewModel
import com.example.tictactoe.app.player.choosecharacter1.ChooseCharacter1ViewModel
import com.example.tictactoe.app.player.choosecharacter2.ChooseCharacter2ViewModel
import com.example.tictactoe.app.scoreboard.ScoreboardViewModel
import com.example.tictactoe.db.TicTacToeDatabase
import com.example.tictactoe.db.dao.PlayerDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chooseCharacter1viewModelModule = module {
    viewModel {
        ChooseCharacter1ViewModel(get(), get())
    }
}

val chooseCharacter2ViewModelModule = module {
    viewModel {
        ChooseCharacter2ViewModel(get(), get())
    }
}

val scoreboardViewModelModule = module {
    viewModel {
        ScoreboardViewModel(get())
    }
}

val playViewModelModule = module {
    viewModel {
        PlayViewModel(get(), get())
    }
}

val databaseModule = module {

    fun provideDatabase(application: Application): TicTacToeDatabase {
        return Room.databaseBuilder(application, TicTacToeDatabase::class.java, "tictactoe_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: TicTacToeDatabase): PlayerDAO {
        return database.playerDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideRepository(dao: PlayerDAO): Repository {
        return Repository(dao)
    }

    single { provideRepository(get()) }
}

val playModelModule = module {
    single{ PlayModel() }
}