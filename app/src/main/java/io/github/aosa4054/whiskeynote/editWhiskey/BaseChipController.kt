package io.github.aosa4054.whiskeynote.editWhiskey

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList

abstract class BaseChipController(private val size: Int) {
    val isChecked: ObservableList<Boolean> = ObservableArrayList<Boolean>()
    var checkedPosition = 100
    init { for (i in 0..size) isChecked.add(false) }
    fun onCheckChanged(index: Int){
        for (i in 0..size){isChecked[i] = false}
        if (checkedPosition != index){
            checkedPosition = index
            isChecked[index] = true
        } else {
            checkedPosition = 100
        }
    }

    abstract fun getCheckedType(): String
}