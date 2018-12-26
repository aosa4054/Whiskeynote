package io.github.aosa4054.whiskeynote.extention

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

fun AppCompatImageView.setRoundImage(blob: ByteArray, context: Context){
    val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)
    val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
    roundedBitmapDrawable.cornerRadius = 350f
    this.setImageDrawable(roundedBitmapDrawable)
}