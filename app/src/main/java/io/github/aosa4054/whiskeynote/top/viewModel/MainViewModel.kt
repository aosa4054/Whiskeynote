package io.github.aosa4054.whiskeynote.top.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.experimental.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WhiskeyRepository(application)
    var whiskeys: List<Whiskey> = emptyList()
    var whiskeySizeText: String = ""

    init {
        launch {
            whiskeys= repository.getAllWhiskeys()
            whiskeySizeText = "${whiskeys.size}件のウイスキー"
        }
    }

    fun reCountWhiskeySize(){
        whiskeySizeText = "${whiskeys.size}件のウイスキー"
    }

    fun deleteWhiskey(name: String){
        repository.delete(name)
    }
}
