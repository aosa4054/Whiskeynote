package io.github.aosa4054.whiskeynote.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.list_item_top.view.*

class MainRecyclerAdapter(var whiskeys: List<Whiskey>):
        RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount() = whiskeys.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val uri = whiskeys[position].image.toUri()
        holder.image.setImageURI(uri)    //角丸つけよう
        holder.name.text = whiskeys[position].name
        holder.kind.text = whiskeys[position].type
    }

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.image_top_list
        val name = view.name_top_list
        val kind = view.kind_top_list
    }
}