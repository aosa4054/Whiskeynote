package io.github.aosa4054.whiskeynote.editWhiskey

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.EmptyCoroutineContext

class EditWhiskeyViewModel: ViewModel(), KoinComponent {
    private val repository: WhiskeyRepository by inject()

    //TODO: 消すよ
    var fruityAverageText: String = ""
    var smokeyAverageText: String = ""
    var saltyAverageText: String = ""
    var maltyAverageText: String = ""
    var floralAverageText: String = ""
    var woodyAverageText: String = ""

    private var navigator: EditwhiskeyNavigator? = null

    //
    private val scotchChipController =  ScotchChipController()
    private val japaneseChipController = JapaneseChipController()
    private val americanChipController = AmericanChipController()
    private val irishChipController = IrishChipController()
    private val canadianChipController = CanadianChipController()
    //

    fun setNavigator(editWhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editWhiskeyNavigator
    }

    fun bind(binding: FragmentEditWhiskeyBinding): FragmentEditWhiskeyBinding{
        binding.scotch = scotchChipController
        binding.japanese = japaneseChipController
        binding.american = americanChipController
        binding.irish = irishChipController
        binding.canadian = canadianChipController
        return binding
    }

    fun getTypes(type: Int): String?{
        return when(type){
            0 -> scotchChipController.getCheckedType()
            1 -> japaneseChipController.getCheckedType()
            2 -> americanChipController.getCheckedType()
            3 -> irishChipController.getCheckedType()
            4 -> canadianChipController.getCheckedType()
            else -> ""
        }
    }

    //<editor-fold desc="toast helper texts about each tastes">
    fun showHelpFruity(){

    }

    fun showHelpSmokey() {
    }

    fun showHelpSalty() {
    }

    fun showHelpMalty(){

    }

    fun showHelpFloral(){

    }

    fun showHelpWoody(){

    }
    //</editor-fold>

    fun save(whiskey: Whiskey){
        CoroutineScope(EmptyCoroutineContext).launch {
            repository.insert(whiskey)
        }
    }

    class ScotchChipController: BaseChipController(7){
        override fun getCheckedType(): String{
            return when (checkedPosition){
                0 -> "ブレンデッド"
                1 -> "スペイサイド"
                2 -> "ハイランド"
                3 -> "ローランド"
                4 -> "アイラ"
                5 -> "アイランズ"
                6 -> "キャンベルタウン"
                else -> "その他・わからない"
            }
        }
    }

    class JapaneseChipController: BaseChipController(3){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                0 -> "シングルモルト"
                1 -> "グレーン"
                2 -> "ブレンデッド"
                else -> "その他・わからない"
            }
        }
    }

    class AmericanChipController: BaseChipController(7){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                0 -> "バーボン"
                1 -> "コーン"
                2 -> "モルト"
                3 -> "ライ"
                4 -> "ホイート"
                5 -> "ブレンデッド"
                6 -> "テネシー"
                else -> "その他・わからない"
            }
        }
    }

    class IrishChipController: BaseChipController(4){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                0 -> "ピュアポットスティル"
                1 -> "モルト"
                2 -> "グレーン"
                3 -> "ブレンデッド"
                else -> "その他・わからない"
            }
        }
    }

    class CanadianChipController: BaseChipController(2){
        override fun getCheckedType(): String{
            return when (checkedPosition){
                0 -> "ブレンデッド"
                1 -> "シングルモルト"
                else -> "その他・わからない"
            }
        }
    }


    class ChipController{
        private val isChecked: ObservableField<Boolean> = ObservableField()
        init { isChecked.set(false) }
        fun onCheckChanged(){
            isChecked.set(!isChecked.get()!!)
        }
        fun whetherIsChecked() = if (isChecked.get()!!) 1 else 0
    }

}
