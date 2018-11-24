package io.github.aosa4054.whiskeynote.top

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.list_item_top.view.*
import android.graphics.BitmapFactory


class MainRecyclerAdapter(var whiskeys: List<Whiskey>,val context: Context,
                          val itemClick: (String) -> Unit, val itemLongClick: (String) -> Boolean):
        RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top, parent, false)
        return MainViewHolder(v, itemClick, itemLongClick)
    }

    override fun getItemCount() = whiskeys.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val blob = whiskeys[position].blob
        val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)

        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
        roundedBitmapDrawable.cornerRadius = 50f
        holder.image.setImageDrawable(roundedBitmapDrawable)
        holder.name.text = whiskeys[position].name
        holder.kind.text = whiskeys[position].type
    }

    class MainViewHolder(view: View, val itemClick: (String) -> Unit, val itemLongClick: (String) -> Boolean): RecyclerView.ViewHolder(view){
        val image = view.image_top_list
        val name = view.name_top_list
        val kind = view.kind_top_list

        init {
            this.itemView.setOnClickListener{ itemClick(name.text.toString()) }
            this.itemView.setOnLongClickListener { itemLongClick(name.text.toString()) }
        }
    }
}