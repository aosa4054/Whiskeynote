package io.github.aosa4054.whiskeynote.top

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Base64
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*
import kotlinx.android.synthetic.main.list_item_top.view.*
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawable


class MainRecyclerAdapter(var whiskeys: List<Whiskey>,val context: Context, val itemClick: (String) -> Unit):
        RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top, parent, false)
        return MainViewHolder(v, itemClick)
    }

    override fun getItemCount() = whiskeys.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val blob = whiskeys[position].blob
        val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)

        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
        roundedBitmapDrawable.cornerRadius = 350f
        holder.image.setImageDrawable(roundedBitmapDrawable)  //角丸つけよう
        holder.name.text = whiskeys[position].name
        holder.kind.text = whiskeys[position].type
    }

    class MainViewHolder(view: View, val itemClick: (String) -> Unit): RecyclerView.ViewHolder(view){
        val image = view.image_top_list
        val name = view.name_top_list
        val kind = view.kind_top_list

        fun setListener(){
            this.itemView.setOnClickListener{itemClick(name.text.toString())}
        }
    }
}