package io.github.aosa4054.whiskeynote.whiskeyDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.list_item_taste.view.*
import kotlinx.android.synthetic.main.list_item_taste_characteristic.view.*

class TasteRecyclerAdapter(val context: Context,
                           private val isCharacteristic: Boolean,
                           private val intFlagList: List<Int>):
        RecyclerView.Adapter<TasteRecyclerAdapter.ViewHolder>() {

    private var everSet = false
    private var firstCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
                if (isCharacteristic) R.layout.list_item_taste_characteristic else R.layout.list_item_taste,
                parent, false)
        return ViewHolder(v, isCharacteristic)
    }

    override fun getItemCount() = intFlagList.count { it == if (isCharacteristic) 2 else 1 }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun setUpImage(id: Int){holder.image.setImageDrawable(ContextCompat.getDrawable(context, id))}

        val tasteList: MutableList<Int> = mutableListOf()
        intFlagList.forEachIndexed{ index, intFlag ->
            if (intFlag == if (isCharacteristic) 2 else 1) tasteList.add(index)
        }

        when(tasteList[position]){
            0  -> setUpImage(R.drawable.ic_citrus)
            1  -> setUpImage(R.drawable.ic_berry)
            2  -> setUpImage(R.drawable.ic_fruity)
            3  -> setUpImage(R.drawable.ic_sea)
            4  -> setUpImage(R.drawable.ic_soil)
            5  -> setUpImage(R.drawable.ic_salt)
            6  -> setUpImage(R.drawable.ic_smokey)
            7  -> setUpImage(R.drawable.ic_chemical)
            8  -> setUpImage(R.drawable.ic_vanilla)
            9  -> setUpImage(R.drawable.ic_barrel)
            10 -> setUpImage(R.drawable.ic_honey)
            11 -> setUpImage(R.drawable.ic_chocolate)
            12 -> setUpImage(R.drawable.ic_spices)
            13 -> setUpImage(R.drawable.ic_herbs)
            else -> { /*do nothing*/ } //TODO: ここの設定
        }

    }

    class ViewHolder(view: View, isCharacteristic: Boolean): RecyclerView.ViewHolder(view){
        var image = if (isCharacteristic) view.imageCharacteristicTaste else view.imageTaste

        fun set(context: Context, id: Int){
            image.setImageDrawable(ContextCompat.getDrawable(context, id))
        }
    }
}