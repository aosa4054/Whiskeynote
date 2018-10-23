package io.github.aosa4054.whiskeynote.editWhiskey

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository

class EditWhiskeyViewModel(application: Application) : AndroidViewModel(application) {
    //TODO: Implement the ViewModel
    //private val repository = WhiskeyRepository(application)
    //TODO: Cannot access database on the main thread (WhiskeyRepository.kt:13)
    private var navigator: EditwhiskeyNavigator? = null

    fun setNavigator(editwhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editwhiskeyNavigator
    }

    fun saveWhiskey(){
        //repository.insert()
        //finish
    }



}
