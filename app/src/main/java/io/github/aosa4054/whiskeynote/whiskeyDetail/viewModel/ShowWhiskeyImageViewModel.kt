package io.github.aosa4054.whiskeynote.whiskeyDetail.viewModel

import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ShowWhiskeyImageViewModel: ViewModel(), KoinComponent {

    private val repository: WhiskeyRepository by inject()
    private lateinit var listener: ShowImageListener

    fun setListener(listener: ShowImageListener){
        this.listener = listener
    }

    fun activateImage(whiskeyName: String){
        GlobalScope.launch(Dispatchers.Main) {
            listener.setImages(repository.getWhiskeyByName(whiskeyName).blob!!)
        }
    }

    interface ShowImageListener{
        fun setImages(blob: ByteArray)
    }
}