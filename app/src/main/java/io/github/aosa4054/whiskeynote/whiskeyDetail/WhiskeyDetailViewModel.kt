package io.github.aosa4054.whiskeynote.whiskeyDetail

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.R
import kotlinx.coroutines.experimental.launch

class WhiskeyDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WhiskeyRepository(application)
    lateinit var whiskey: Whiskey

    var name = ""
    var type = ""
    var kind: String? = null
    var fruity = 0
    var smokey = 0
    var salty = 0
    var malty = 0
    var floral = 0
    var woody = 0
    var memo: String? = null
    lateinit var blob: ByteArray


    fun setUpWhiskey(whiskeyName: String){
        launch {
            whiskey = repository.getWhiskeyByName(whiskeyName)
            name = whiskey.name
            type = whiskey.type
            kind = whiskey.kind
            fruity = whiskey.fruity
            smokey = whiskey.smokey
            salty = whiskey.salty
            malty = whiskey.malty
            floral = whiskey.floral
            woody = whiskey.woody
            memo = whiskey.memo
            blob = whiskey.blob
        }
    }
}
