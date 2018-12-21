package io.github.aosa4054.whiskeynote.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.koin.standalone.KoinComponent

class WhiskeyRepository(val dao: WhiskeyDao): KoinComponent {

    val mAllWhiskeys: LiveData<List<Whiskey>> = dao.getAllWhiskeys()

    @WorkerThread
    fun getWhiskeyByName(key: String): Whiskey = dao.getWhiskeyById(key)

    @WorkerThread
    fun insert(whiskey: Whiskey) {
        dao.insert(whiskey)
    }

    @WorkerThread
    fun delete(key: String){
        dao.deleteWhiskeyById(key)
    }
}