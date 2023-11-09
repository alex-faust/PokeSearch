package com.example.pokesearch.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.pokesearch.R

class CanvasFrame(context: Context) : View(context) {

    val blurRadius = 7.5f
    private val drawColor = ResourcesCompat.getColor(resources, R.color.color1, null)
    private val shadowColor = ResourcesCompat.getColor(resources, R.color.black, null)

    private val paint = Paint().apply {
        color = drawColor
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeWidth = 10f
        //maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER)
        //setShadowLayer(10f, 0f, 0f, shadowColor)
    }
    private lateinit var frame: RectF
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
                val inset = 80f
        frame = RectF(inset, inset*2, width - inset, height - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(frame, 50f, 50f, paint)
    }




}
