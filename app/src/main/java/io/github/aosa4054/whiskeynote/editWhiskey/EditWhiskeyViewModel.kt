package io.github.aosa4054.whiskeynote.editWhiskey

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.EmptyCoroutineContext

class EditWhiskeyViewModel: ViewModel(), KoinComponent {
    private val repository: WhiskeyRepository by inject()

    private var navigator: EditwhiskeyNavigator? = null
    var chips = ChipController()

    fun setNavigator(editWhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editWhiskeyNavigator
    }

    fun save(whiskey: Whiskey){
        CoroutineScope(EmptyCoroutineContext).launch {
            repository.insert(whiskey)
        }
    }

    fun duplicates(whiskeyName: String): Boolean{
        if (repository.mAllWhiskeys.value == null) {
            return false //FIXME: どうなん
        }
        return repository.mAllWhiskeys.value!!.map { it.name }.contains(whiskeyName)
    }

    class ChipController{
        val isChecked: ObservableList<Boolean> = ObservableArrayList()
        init { for (i in 0 .. 6) isChecked.add(false) } // i in size of chip group
        fun onCheckChanged(index: Int){ isChecked[index] = !isChecked[index] }
        fun whetherIsChecked(index: Int) = if ( isChecked[index] ) 1 else 0
    }

}
