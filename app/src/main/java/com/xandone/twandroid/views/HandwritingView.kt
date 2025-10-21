package com.xandone.twandroid.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.digitalink.Ink
import com.xandone.twandroid.mltik.StrokeManager
import kotlin.math.max
import kotlin.math.min
import androidx.core.graphics.createBitmap

/**
 * @author: xiao
 * created on: 2025/10/20 15:53
 * description:
 */
class HandwritingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(
        context, attrs
    ) {

    private var recognizedStrokePaint: Paint
    private var textPaint: TextPaint
    private var currentStrokePaint: Paint
    private var canvasPaint: Paint

    private var currentStroke: Path
    private var drawCanvas: Canvas? = null
    private var canvasBitmap: Bitmap? = null
    private var strokeManager: StrokeManager? = null


    init {
        currentStrokePaint = Paint()
        currentStrokePaint.color = Color.BLACK
        currentStrokePaint.isAntiAlias = true
        currentStrokePaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, STROKE_WIDTH_DP.toFloat(), resources.displayMetrics
        )
        currentStrokePaint.style = Paint.Style.STROKE
        currentStrokePaint.strokeJoin = Paint.Join.ROUND
        currentStrokePaint.strokeCap = Paint.Cap.ROUND

        recognizedStrokePaint = Paint(currentStrokePaint)
        recognizedStrokePaint.color = Color.BLACK

        textPaint = TextPaint()
        textPaint.color = Color.GREEN

        currentStroke = Path()
        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    private fun computeBoundingBox(ink: Ink): Rect {
        var top = Float.MAX_VALUE
        var left = Float.MAX_VALUE
        var bottom = Float.MIN_VALUE
        var right = Float.MIN_VALUE
        for (s in ink.strokes) {
            for (p in s.points) {
                top = min(top.toDouble(), p.y.toDouble()).toFloat()
                left = min(left.toDouble(), p.x.toDouble()).toFloat()
                bottom = max(bottom.toDouble(), p.y.toDouble()).toFloat()
                right = max(right.toDouble(), p.x.toDouble()).toFloat()
            }
        }
        val centerX = (left + right) / 2
        val centerY = (top + bottom) / 2
        val bb = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        bb.union(
            (centerX - MIN_BB_WIDTH / 2).toInt(),
            (centerY - MIN_BB_HEIGHT / 2).toInt(),
            (centerX + MIN_BB_WIDTH / 2).toInt(),
            (centerY + MIN_BB_HEIGHT / 2).toInt()
        )
        return bb
    }

    fun setStrokeManager(strokeManager: StrokeManager) {
        this.strokeManager = strokeManager
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        canvasBitmap = createBitmap(width, height)
        drawCanvas = Canvas(canvasBitmap!!)
        invalidate()
    }

    private fun redrawContent() {
        clear()
        val currentInk = strokeManager!!.currentInk
        drawInk(currentInk, currentStrokePaint)

        val content = strokeManager!!.content
        for (ri in content) {
            drawInk(ri.ink, recognizedStrokePaint)
            val bb = computeBoundingBox(ri.ink)
            drawTextIntoBoundingBox(ri.text, bb, textPaint)
        }
        invalidate()
    }

    private fun drawTextIntoBoundingBox(text: String, bb: Rect, textPaint: TextPaint) {
        val arbitraryFixedSize = 20f
        textPaint.textSize = arbitraryFixedSize
        textPaint.textScaleX = 1f

        val r = Rect()
        textPaint.getTextBounds(text, 0, text.length, r)

        val textSize = arbitraryFixedSize * bb.height().toFloat() / r.height().toFloat()
        textPaint.textSize = textSize

        textPaint.getTextBounds(text, 0, text.length, r)

        textPaint.textScaleX = bb.width().toFloat() / r.width().toFloat()

        drawCanvas!!.drawText(text, bb.left.toFloat(), bb.bottom.toFloat(), textPaint)
    }

    private fun drawInk(ink: Ink, paint: Paint) {
        for (s in ink.strokes) {
            drawStroke(s, paint)
        }
    }

    private fun drawStroke(s: Ink.Stroke, paint: Paint) {
        var path: Path? = null
        for (p in s.points) {
            if (path == null) {
                path = Path()
                path.moveTo(p.x, p.y)
            } else {
                path.lineTo(p.x, p.y)
            }
        }
        drawCanvas!!.drawPath(path!!, paint)
    }

    private fun clear() {
        currentStroke.reset()
        onSizeChanged(
            canvasBitmap!!.width,
            canvasBitmap!!.height,
            canvasBitmap!!.width,
            canvasBitmap!!.height
        )
    }

    fun clearCanvas() {
        clear()
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)
        canvas.drawPath(currentStroke, currentStrokePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val x = event.x
        val y = event.y

        when (action) {
            MotionEvent.ACTION_DOWN -> currentStroke.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> currentStroke.lineTo(x, y)
            MotionEvent.ACTION_UP -> {
                currentStroke.lineTo(x, y)
                drawCanvas!!.drawPath(currentStroke, currentStrokePaint)
                currentStroke.reset()
            }

            else -> {}
        }
        strokeManager!!.addNewTouchEvent(event)
        invalidate()
        return true
    }


    companion object {
        const val STROKE_WIDTH_DP: Int = 3
        const val MIN_BB_WIDTH: Int = 10
        const val MIN_BB_HEIGHT: Int = 10
    }

}