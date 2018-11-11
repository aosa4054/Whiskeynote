package io.github.aosa4054.whiskeynote.top.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel;
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.experimental.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository = WhiskeyRepository(application)
    val whiskeys: List<Whiskey> by lazy {
        lateinit var allWhiskeys: List<Whiskey>
        launch { allWhiskeys = repository.getAllWhiskeys() }
        allWhiskeys
    }
}
