package io.github.aosa4054.whiskeynote.editWhiskey

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.coroutines.experimental.launch

class EditWhiskeyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WhiskeyRepository(application)
    private lateinit var whiskeys: List<Whiskey>
    var fruityAverageText: String = ""
    var smokeyAverageText: String = ""
    var saltyAverageText: String = ""
    var maltyAverageText: String = ""
    var floralAverageText: String = ""
    var woodyAverageText: String = ""

    init {
        launch {
            whiskeys = repository.getAllWhiskeys()
            if (!whiskeys.isEmpty()){
                fruityAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.fruity }.average()}"
                smokeyAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.smokey }.average()}"
                saltyAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.salty }.average()}"
                maltyAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.malty }.average()}"
                floralAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.floral }.average()}"
                woodyAverageText = "これまで飲んだウイスキーの平均：${whiskeys.map { it.woody }.average()}"
            }
        }
    }

    private var navigator: EditwhiskeyNavigator? = null

    private val scotchChipController =  ScotchChipController()
    private val japaneseChipController = JapaneseChipController()
    private val americanChipController = AmericanChipController()
    private val irishChipController = IrishChipController()
    private val canadianChipController = CanadianChipController()

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

    fun save(whiskey: Whiskey){
        repository.insert(whiskey)
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

}
