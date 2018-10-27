package io.github.aosa4054.whiskeynote.editWhiskey

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding

class EditWhiskeyViewModel(application: Application) : AndroidViewModel(application) {
    //TODO: Implement the ViewModel
    //private val repository = WhiskeyRepository(application)
    //TODO: Cannot access database on the main thread (WhiskeyRepository.kt:13)
    private var navigator: EditwhiskeyNavigator? = null
    val scotchChipController =  ScotchChipController()

    fun setNavigator(editwhiskeyNavigator: EditwhiskeyNavigator){
        navigator = editwhiskeyNavigator
    }

    fun bind(binding: FragmentEditWhiskeyBinding): FragmentEditWhiskeyBinding{
        binding.scotch = scotchChipController
        return binding
    }

    private fun getTypes(type: Int): String?{
        return when(type){
            0 -> scotchChipController.getCheckedType()
            else -> ""
        }
    }

    fun saveWhiskey(type: Int){
        val kind = getTypes(type)
        //repository.insert()
        //finish
    }

    class ScotchChipController{
        val isChecked: ObservableList<Boolean> = ObservableArrayList<Boolean>()
        init { for (i in 0 until 3) isChecked.add(false) }
        fun onCheckChanged(index: Int){ isChecked[index] = !isChecked[index] }
        fun getCheckedType(): String{
            var types = ""
            for (j in 0 until 3){
                if (isChecked[j]){
                    when (j){
                        0 -> types += "ブレンデッド:"
                        1 -> types += "スペイサイド:"
                        2 -> types += "ハイランド:"
                        3 -> types += "ローランド:"
                        4 -> types += "アイラ:"
                        5 -> types += "アイランズ:"
                        6 -> types += "キャンベルタウン:"
                        7 -> types += "その他・わからない:"
                    }
                }
            }
            return types
        }
    }


}
