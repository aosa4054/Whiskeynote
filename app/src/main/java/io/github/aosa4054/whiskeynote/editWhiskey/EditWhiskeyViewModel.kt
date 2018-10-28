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

    fun setNavigator(editWhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editWhiskeyNavigator
    }

    fun bind(binding: FragmentEditWhiskeyBinding): FragmentEditWhiskeyBinding{
        binding.scotch = scotchChipController
        return binding
    }

    fun getTypes(type: Int): String?{
        return when(type){
            0 -> scotchChipController.getCheckedType()
            else -> ""
        }
    }

    class ScotchChipController{
        val isChecked: ObservableList<Boolean> = ObservableArrayList<Boolean>()
        var checkedPosition = 100
        init { for (i in 0..7) isChecked.add(false) }
        fun onCheckChanged(index: Int){
            for (i in 0..7){isChecked[i] = false}
            if (checkedPosition != index){
                checkedPosition = index
                isChecked[index] = true
            } else {
                checkedPosition = 100
            }
            Log.d("チップ配列", isChecked.toString())
            Log.d("チップ配列場所", checkedPosition.toString())
        }
        fun getCheckedType(): String{
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


}
