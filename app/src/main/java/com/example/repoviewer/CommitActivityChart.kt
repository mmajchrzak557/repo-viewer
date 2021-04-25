package com.example.repoviewer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat

@SuppressLint("ViewConstructor")
class CommitActivityChart(data: List<WeeklyCommitActivity>, context: Context) : View(context) {
    private var pointsY = data.map { it.total.toFloat() }
    private var pointsX = (1..52).toList()
    private val xOffset = 50f
    private val yOffset = 50f
    private var parentWidth: Int? = null
    private var parentHeight: Int? = null

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorChart)
        strokeWidth = 8f
        isAntiAlias = true
        isDither = true
        strokeCap = Paint.Cap.ROUND
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Scaling x and y values so the chart is visible and fits the layout
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        pointsX = pointsX.map { (it / 52f * (parentWidth!! - xOffset * 2) + xOffset).toInt() }
        val maxValue = pointsY.max()!!
        val scaleY = if(maxValue == 0f){
            1f
        }else{
            (parentHeight!! - 2 * yOffset) / maxValue
        }
        pointsY = pointsY.map { parentHeight!! - it * scaleY - yOffset}
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until pointsX.size - 1) {
            canvas!!.drawLine(pointsX[i].toFloat(), pointsY[i], pointsX[i + 1].toFloat(), pointsY[i + 1], paint)
        }
        super.onDraw(canvas)
    }

}