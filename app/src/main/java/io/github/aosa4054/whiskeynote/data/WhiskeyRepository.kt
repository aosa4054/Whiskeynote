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
        //return async { mWhiskeyDao.getAllWhiskeys() }.await()
        try {
            lateinit var a: List<Whiskey>
            launch {
                a=mWhiskeyDao.getAllWhiskeys()
            }.join()
            return a
        }catch (e: IllegalStateException){
            Toast.makeText(application, "データが読み込めませんでした。もう一度お試しください", Toast.LENGTH_LONG).show()
            return emptyList()
        }
    }


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