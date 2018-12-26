package io.github.aosa4054.whiskeynote.top

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.list_item_top.view.*
import androidx.core.content.ContextCompat
import io.github.aosa4054.whiskeynote.extention.setRoundImage


class MainRecyclerAdapter(val context: Context,
                          val itemClick: (String) -> Unit,
                          val itemLongClick: (String) -> Boolean):
        RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    private var whiskeys = emptyList<Whiskey>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top, parent, false)
        return MainViewHolder(v, itemClick, itemLongClick)
    }

    override fun getItemCount() = whiskeys.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        if (whiskeys[position].blob != null) {
            val blob = whiskeys[position].blob!!
            holder.image.setRoundImage(blob, context)
        } else {
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_deffault_image))
        }

        holder.name.text = whiskeys[position].name
        holder.kind.text = whiskeys[position].type
    }

    fun setWhiskeys(whiskeys: List<Whiskey>){
        this.whiskeys = whiskeys
        notifyDataSetChanged()
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