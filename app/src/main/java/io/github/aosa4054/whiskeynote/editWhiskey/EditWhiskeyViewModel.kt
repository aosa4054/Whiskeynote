package io.github.aosa4054.whiskeynote.editWhiskey

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel

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

    class ScotchChipController{
        val isChecked: ObservableList<Boolean> = ObservableArrayList<Boolean>()
        init { for (i in 0 until 3) isChecked.add(false) }
        fun onCheckChanged(index: Int){ isChecked[index] = !isChecked[index] }
    }


}
