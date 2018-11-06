package io.github.aosa4054.whiskeynote.top

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel;
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository = WhiskeyRepository(application)
}
