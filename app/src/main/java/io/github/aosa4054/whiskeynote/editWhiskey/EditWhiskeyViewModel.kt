package io.github.aosa4054.whiskeynote.editWhiskey

import android.provider.Contacts
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

class EditWhiskeyViewModel: ViewModel(), KoinComponent {
    private val repository: WhiskeyRepository by inject()
    var whiskeysLiveData: LiveData<List<Whiskey>>
    private var whiskeys: List<Whiskey>? = emptyList()

    private var navigator: EditwhiskeyNavigator? = null
    var chips = ChipController()

    init {
        whiskeysLiveData = repository.mAllWhiskeys
        whiskeys = whiskeysLiveData.value
    }

    fun resetWhiskeys(data: List<Whiskey>?){
        whiskeys = data
    }

    fun setNavigator(editWhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editWhiskeyNavigator
    }

    fun save(whiskey: Whiskey){
        CoroutineScope(EmptyCoroutineContext).launch {
            repository.insert(whiskey)
        }
    }

    fun duplicates(whiskeyName: String): Boolean{
        if (whiskeys == null) {
            return false
        }
        return whiskeys!!.map { it.name }.contains(whiskeyName)
    }

    class ChipController{
        val isChecked: ObservableList<Boolean> = ObservableArrayList()
        init { for (i in 0 .. 6) isChecked.add(false) } // i in size of chip group
        fun onCheckChanged(index: Int){ isChecked[index] = !isChecked[index] }
        fun whetherIsChecked(index: Int) = if ( isChecked[index] ) 1 else 0
    }

}
