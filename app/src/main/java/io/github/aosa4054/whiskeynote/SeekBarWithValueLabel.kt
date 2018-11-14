package io.github.aosa4054.whiskeynote

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.core.content.ContextCompat


class SeekBarWithValueLabel: SeekBar {
    constructor(context: Context): super(context){ setUpLabel(context) }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs){ setUpLabel(context) }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){ setUpLabel(context) }

    private val textPaint = Paint()

    private fun setUpLabel(context: Context){
        textPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 28f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = thumb.bounds.centerX() + 12f
        val y = 28f

        canvas?.drawText(progress.toString(), x, y, textPaint)
    }

}