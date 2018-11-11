package io.github.aosa4054.whiskeynote.data

import android.app.Application
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class WhiskeyRepository(application: Application) {
    private var mWhiskeyDao: WhiskeyDao = WhiskeyDatabase.getInstance(application).whiskeyDao()
    private lateinit var mAllWhiskeys: List<Whiskey>

    suspend fun getAllWhiskeys(): List<Whiskey>{
        return async { mWhiskeyDao.getAllWhiskeys() }.await()
    }

    fun insert(whiskey: Whiskey): Job {
        return launch {
            mWhiskeyDao.insert(whiskey)
        }
    }
}