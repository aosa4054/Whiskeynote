package io.github.aosa4054.whiskeynote.whiskeyDetail

import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.EmptyCoroutineContext

class WhiskeyDetailViewModel: ViewModel(), KoinComponent {

    private val repository: WhiskeyRepository by inject()
    private lateinit var listener: WhiskeyDetailListener
    lateinit var whiskey: Whiskey

    var name = ""
    var type = ""
    var kind = ""

    var isDelicate = 0
    var isLight = 0
    var isMild = 0
    var isComplex = 0
    var isRich = 0
    var isElegant = 0
    var isFlesh = 0

    var citrus = 0
    var berry = 0
    var fruity = 0
    var sea = 0
    var soil = 0
    var salt = 0
    var smokey = 0
    var chemical = 0
    var vanilla = 0
    var barrel = 0
    var honey = 0
    var chocolate = 0
    var spices = 0
    var herbs = 0

    var memo: String? = null
    var blob: ByteArray? = null

    fun setListener(listener: WhiskeyDetailListener){
        this.listener = listener
    }

    fun setUpWhiskey(whiskeyName: String){
        CoroutineScope(EmptyCoroutineContext).launch {
            whiskey = repository.getWhiskeyByName(whiskeyName)

            setWhiskeyData(whiskey)

            listener.setImage(blob)
        }
    }

    private suspend fun setWhiskeyData(it: Whiskey){
        this.name = it.name
        this.type = it.type
        this.kind = it.kind

        this.isDelicate = it.isDelicate
        this.isLight = it.isLight
        this.isMild = it.isMild
        this.isComplex = it.isComplex
        this.isRich = it.isRich
        this.isElegant = it.isElegant
        this.isFlesh = it.isFlesh

        this.citrus = it.citrus
        this.berry = it.berry
        this.fruity = it.fruity
        this.sea = it.sea
        this.soil = it.soil
        this.salt = it.salt
        this.smokey = it.smokey
        this.chemical = it.chemical
        this.vanilla = it.vanilla
        this.barrel = it.barrel
        this.chocolate = it.chocolate
        this.spices = it.spices
        this.herbs = it.herbs

        this.memo = it.memo
        this.blob = it.blob
    }

    interface WhiskeyDetailListener{
        fun setImage(blob: ByteArray?)
    }
}
