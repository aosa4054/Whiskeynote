package io.github.aosa4054.whiskeynote.whiskeyDetail

import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class WhiskeyDetailViewModel: ViewModel(), KoinComponent {

    private val repository: WhiskeyRepository by inject()
    private var listener: WhiskeyDetailListener? = null
    lateinit var whiskey: Whiskey

    var name = ""
    var type = ""
    var kind = ""

    var typeAndKind = ""

    var isDelicate = 0
    var isLight = 0
    var isMild = 0
    var isComplex = 0
    var isRich = 0
    var isElegant = 0
    var isFlesh = 0

    var tasteFlags: MutableList<Int> = mutableListOf()

    var memo: String? = null
    var blob: ByteArray? = null

    fun setListener(listener: WhiskeyDetailListener){
        this.listener = listener
    }

    fun setUpWhiskey(whiskeyName: String){
        whiskey = repository.getWhiskeyByName(whiskeyName)
        setWhiskeyData(whiskey)
        listener?.setImages(blob)
    }

    private fun setWhiskeyData(it: Whiskey){
        this.name = it.name
        this.type = it.type
        this.kind = it.kind

        this.typeAndKind = "${type} / ${kind}"

        this.isDelicate = it.isDelicate
        this.isLight = it.isLight
        this.isMild = it.isMild
        this.isComplex = it.isComplex
        this.isRich = it.isRich
        this.isElegant = it.isElegant
        this.isFlesh = it.isFlesh

        tasteFlags.addAll(listOf(
                it.citrus,
                it.berry,
                it.fruity,
                it.sea,
                it.soil,
                it.salt,
                it.smokey,
                it.chemical,
                it.vanilla,
                it.barrel,
                it.honey,
                it.chocolate,
                it.spices,
                it.herbs
        ))


        this.memo = it.memo
        this.blob = it.blob
    }

    fun onStop(){
        tasteFlags.removeAll(tasteFlags)
    }

    interface WhiskeyDetailListener{
        fun setImages(blob: ByteArray?)
    }
}
