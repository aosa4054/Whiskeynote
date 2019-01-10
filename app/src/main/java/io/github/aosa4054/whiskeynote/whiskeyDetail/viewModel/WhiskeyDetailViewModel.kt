package io.github.aosa4054.whiskeynote.whiskeyDetail.viewModel

import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class WhiskeyDetailViewModel: ViewModel(), KoinComponent {

    fun <T> MutableList<T>.replace(elements: Collection<T>){
        this.removeAll(this)
        this.addAll(elements)
    }

    private val repository: WhiskeyRepository by inject()
    private var listener: WhiskeyDetailListener? = null
    lateinit var whiskey: Whiskey

    var name = ""
    var type = ""
    var kind = ""

    var typeAndKind = ""

    var isDelicate = 2
    var isLight = 2
    var isMild = 2
    var isComplex = 2
    var isRich = 2
    var isElegant = 2
    var isFlesh = 2

    var tasteFlags: MutableList<Int> = mutableListOf()
    var features: MutableList<Int> = mutableListOf()

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

        features.replace(listOf(
                it.isDelicate,
                it.isLight,
                it.isMild,
                it.isComplex,
                it.isRich,
                it.isElegant,
                it.isFlesh
        ))

        tasteFlags.replace(listOf(
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
        //tasteFlags.removeAll(tasteFlags)
    }

    interface WhiskeyDetailListener{
        fun setImages(blob: ByteArray?)
    }
}
