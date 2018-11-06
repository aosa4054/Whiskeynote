package io.github.aosa4054.whiskeynote.data

import android.app.Application
import android.os.AsyncTask
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class WhiskeyRepository(application: Application) {
    private var mWhiskeyDao: WhiskeyDao
    private lateinit var mAllWhiskeys: List<Whiskey>

    init {
        val db: WhiskeyDatabase = WhiskeyDatabase.getInstance(application)
        mWhiskeyDao = db.whiskeyDao()
        launch { mAllWhiskeys = getAllWhiskeys() }
    }

    suspend fun getAllWhiskeys(): List<Whiskey>{
        return async { mWhiskeyDao.getAllWhiskeys() }.await()
    }

    //fun getAllWhiskeys(): List<Whiskey> = mAllWhiskeys
    fun getScotch(): List<Whiskey> = mAllWhiskeys.filter { it.type == "スコッチ" }
    fun getJapanese(): List<Whiskey> = mAllWhiskeys.filter { it.type == "ジャパニーズ" }
    fun getAmerican(): List<Whiskey> = mAllWhiskeys.filter { it.type == "アメリカン" }
    fun getIrish(): List<Whiskey> = mAllWhiskeys.filter { it.type == "アイリッシュ" }
    fun getCanadian(): List<Whiskey> = mAllWhiskeys.filter { it.type == "カナディアン" }
    fun getOthers(): List<Whiskey> = mAllWhiskeys.filter { it.type == "その他" }

    fun insert(whiskey: Whiskey): Job {
        return launch {
            mWhiskeyDao.insert(whiskey)
        }
    }
}