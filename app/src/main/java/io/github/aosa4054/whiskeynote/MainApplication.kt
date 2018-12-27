package io.github.aosa4054.whiskeynote

import android.app.Application
import androidx.room.Room
import io.github.aosa4054.whiskeynote.data.WhiskeyDatabase
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module



class MainApplication: Application() {

    val dataBaseModule = module {
        single {
            Room.databaseBuilder(this@MainApplication,
                    WhiskeyDatabase::class.java, "Whiskey.db")
                    //.fallbackToDestructiveMigration()
                    .build() }
        single { WhiskeyRepository(get<WhiskeyDatabase>().whiskeyDao()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(dataBaseModule))
    }
}