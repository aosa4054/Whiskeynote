package io.github.aosa4054.whiskeynote.data

import android.app.Application
import android.os.AsyncTask

class WhiskeyRepository(application: Application) {
    private var mWhiskeyDao: WhiskeyDao
    private var mAllWhiskeys: List<Whiskey>

    init {
        val db: WhiskeyDatabase = WhiskeyDatabase.getInstance(application)
        mWhiskeyDao = db.whiskeyDao()
        mAllWhiskeys = db.whiskeyDao().getAllWhiskeys()
    }

    fun insert (whiskey: Whiskey) = insertAsyncTask(mWhiskeyDao).execute(whiskey)

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: WhiskeyDao) :
            AsyncTask<Whiskey, Void, Void>() {

        override fun doInBackground(vararg params: Whiskey): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}