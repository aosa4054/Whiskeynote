package io.github.aosa4054.whiskeynote.data

import android.app.Application
import android.os.AsyncTask
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

class WhiskeyRepository(application: Application) {
    private var mWhiskeyDao: WhiskeyDao
    private lateinit var mAllWhiskeys: List<Whiskey>

    init {
        val db: WhiskeyDatabase = WhiskeyDatabase.getInstance(application)
        mWhiskeyDao = db.whiskeyDao()
        launch { mAllWhiskeys = db.whiskeyDao().getAllWhiskeys() }
    }

    fun insert(whiskey: Whiskey): Job {
        return launch {
            mWhiskeyDao.insert(whiskey)
        }
    }
}