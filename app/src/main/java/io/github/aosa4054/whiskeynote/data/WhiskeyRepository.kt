package io.github.aosa4054.whiskeynote.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.logging.Handler

class WhiskeyRepository(val application: Application) {
    private var mWhiskeyDao: WhiskeyDao = WhiskeyDatabase.getInstance(application).whiskeyDao()
    private lateinit var mAllWhiskeys: List<Whiskey>

    suspend fun getAllWhiskeys(): List<Whiskey>{
        return try {
            async { mWhiskeyDao.getAllWhiskeys() }.await()
        }catch (e: IllegalStateException){
            emptyList()
        }
    }

    fun insert(whiskey: Whiskey): Job {
        return launch {
            mWhiskeyDao.insert(whiskey)
        }
    }

    fun delete(key: String): Job{
        return launch {
            mWhiskeyDao.deleteWhiskeyById(key)
        }
    }
}