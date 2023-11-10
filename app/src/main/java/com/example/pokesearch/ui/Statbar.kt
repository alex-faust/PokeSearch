package com.example.pokesearch.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.pokesearch.R
import kotlin.properties.Delegates


class StatBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
{

    /*lateinit var square: ImageView
    lateinit var hpNum: String
    lateinit var atkNum: String
    lateinit var defNum: String
    lateinit var spAtkNum: String
    lateinit var spDefNum: String
    lateinit var spdNum: String*/
    //private var valueAnimator = ValueAnimator()

    private var widthSize = 0
    private var heightSize = 0
    private val pointPosition: PointF by lazy {
        PointF((width / 2).toFloat(), (height / 2 + 20).toFloat())
    }

    private var progressW = 0F


    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = resources.getColor(R.color.color2) //use (color, theme)
    }

    private var animation = ValueAnimator()

    private var statBarState: StatBarState by Delegates.observable(StatBarState.Finished) {p,old, new ->
        when (new) {
            StatBarState.Starting -> {
                animation = ValueAnimator.ofFloat(0f, 420f).apply {
                    addUpdateListener { animation  ->
                        progressW = widthSize * animation.animatedValue as Float
                        invalidate()
                    }
                }
                animation.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        progressW = 0f
                    }
                })
                animation.start()
            } else -> {
                animation.cancel()
            progressW = 0f
            invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (statBarState) {
            StatBarState.Starting -> {
                canvas.drawColor(resources.getColor(R.color.color3))
                canvas.drawRect(15f, 115f, progressW, height.toFloat(), barPaint)
            }
            else -> {
                canvas.drawColor(resources.getColor(R.color.color1))
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun updateStatus(state: StatBarState) {
        statBarState = state
    }




}

sealed class StatBarState {
    object Starting: StatBarState()
    object Finished: StatBarState()
}