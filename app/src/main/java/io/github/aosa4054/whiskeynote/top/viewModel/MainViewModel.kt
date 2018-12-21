package io.github.aosa4054.whiskeynote.top.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.EmptyCoroutineContext

class MainViewModel() : ViewModel(), KoinComponent {

    private val repository: WhiskeyRepository by inject()
    var whiskeys: LiveData<List<Whiskey>>
    var whiskeySizeText: String = ""

    init {
        whiskeys = repository.mAllWhiskeys
        if (whiskeys.value != null) whiskeySizeText = "${whiskeys.value!!.size}件のウイスキー"
    }

    fun recountWhiskeySize(){
        whiskeySizeText = "${whiskeys.value!!.size}件のウイスキー"
    }

    fun deleteWhiskey(name: String){
        CoroutineScope(EmptyCoroutineContext).launch {
            repository.delete(name)
        }
    }
}
