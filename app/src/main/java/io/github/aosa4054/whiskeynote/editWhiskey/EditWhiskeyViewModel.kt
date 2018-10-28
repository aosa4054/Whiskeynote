package io.github.aosa4054.whiskeynote.editWhiskey

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding

class EditWhiskeyViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository = WhiskeyRepository(application)
    //TODO: Cannot access database on the main thread (WhiskeyRepository.kt:13)
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

    class JapaneseChipController: BaseChipController(7){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                else -> "その他・わからない"
            }
        }
    }

    class AmericanChipController: BaseChipController(7){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                else -> "その他・わからない"
            }
        }
    }

    class IrishChipController: BaseChipController(7){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                else -> "その他・わからない"
            }
        }
    }

    class CanadianChipController: BaseChipController(7){  //TODO change `size`
        override fun getCheckedType(): String{
            return when (checkedPosition){
                else -> "その他・わからない"
            }
        }
    }

}
