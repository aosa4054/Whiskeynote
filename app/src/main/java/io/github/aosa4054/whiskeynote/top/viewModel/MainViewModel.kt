package io.github.aosa4054.whiskeynote.top.viewModel

import android.app.Application
import android.os.Handler
import android.os.SystemClock.sleep
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel;
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.concurrent.thread

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository = WhiskeyRepository(application)
   var whiskeys: List<Whiskey> = emptyList()

    init {
        launch {
            whiskeys= repository.getAllWhiskeys()
        }
        sleep(30)
        //FIXME: sleepとか使いたくない

    }
}
