package com.lib.print

import android.graphics.*
import com.lib.print.component.Align
import com.lib.print.component.BasePrint
import com.lib.print.component.Vector
import androidx.core.graphics.createBitmap


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class Print : BasePrint(Align.CENTER) {

    companion object {
        const val FEED_SIZE = 12
        var scale = 1f
        var defaultFont: Typeface? = Typeface.MONOSPACE
        var defaultTextSize: Float? = null
    }

    private val items = ArrayList<BasePrint>()
    private var vector = Vector()

    fun config(scale: Float = 1f, defaultFont: Typeface? = null, defaultTextSize: Float? = null) : Print {
        Print.scale = scale
        Print.defaultFont = defaultFont
        Print.defaultTextSize = defaultTextSize
        return this
    }

    fun build(paperWidth: Int = 400) : Bitmap {
        vector.width = (paperWidth * scale).toInt()
        bound(vector)
        height()
        val bitmap = createBitmap(vector.width, vector.height)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        draw(canvas, vector)
        return bitmap
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        items.forEach {
            it.draw(canvas, vector)
            vector.y += it.height()
        }
    }

    fun build() : Bitmap = build(400)

    fun list() : List<BasePrint> = items

    fun add(printItem: BasePrint) : Print {
        items.add(printItem)
        return this
    }

    fun feed(size: Int = FEED_SIZE) : Print {
        items.add(Feed((size * scale).toInt()))
        return this
    }
    fun feed() : Print {
        items.add(Feed(FEED_SIZE))
        return this
    }

    fun singleLine(isDash: Boolean = false) : Print {
        items.add(SingleLine(isDash))
        return this
    }

    fun doubleLineLine(isDash: Boolean = false) : Print {
        items.add(DoubleLine(isDash))
        return this
    }

    override fun bound(vector: Vector) {
        items.forEach {
            it.bound(vector)
        }
    }

    override fun height(): Int {
        vector.height = 0
        items.forEach {
            vector.height += it.height()
        }
        return vector.height
    }

    class Feed(private val size: Int = FEED_SIZE) : BasePrint(Align.CENTER) {
        override fun height(): Int = size

        override fun draw(canvas: Canvas, vector: Vector) {}
    }

    class DoubleLine(private val isDash: Boolean = false, strokeWidth: Float = 2f, val dashWidth: Float = 6f, val dashGap: Float = 4f) : BasePrint(Align.CENTER) {
        private val strokeWidthScale = strokeWidth * scale
        private var padding = (((FEED_SIZE * 2) * scale) - strokeWidthScale) / 2

        override fun height(): Int = (strokeWidthScale + (padding * 2)).toInt()

        override fun draw(canvas: Canvas, vector: Vector) {
            singleLine(canvas, Vector(vector.width, vector.height, vector.x, vector.y))
            singleLine(canvas, Vector(vector.width, vector.height, vector.x, vector.y + (padding.toInt() /2)))
        }

        private fun singleLine(canvas: Canvas, vector: Vector) {
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidthScale
            paint.color = Color.BLACK
            val path = Path()

            if (isDash) {
                val effects = DashPathEffect(floatArrayOf(dashWidth * scale, dashGap * scale, dashWidth * scale, dashGap * scale), 0f)
                paint.pathEffect = effects
            }

            val y = vector.y.toFloat()
            path.moveTo(vector.x.toFloat(), y)
            path.lineTo(vector.x + vector.width.toFloat(), y)
            canvas.drawPath(path, paint)
        }
    }

    class SingleLine(private val isDash: Boolean = false, strokeWidth: Float = 2f, val dashWidth: Float = 6f, val dashGap: Float = 4f) : BasePrint(Align.CENTER) {
        private val strokeWidthScale = strokeWidth * scale
        private var padding = ((FEED_SIZE * scale) - strokeWidth) / 2

            override fun height(): Int = (strokeWidthScale + (padding * 2)).toInt()

        override fun draw(canvas: Canvas, vector: Vector) {
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidthScale
            paint.color = Color.BLACK
            val path = Path()

            if (isDash) {
                val effects = DashPathEffect(floatArrayOf(dashWidth * scale, dashGap * scale, dashWidth * scale, dashGap * scale), 0f)
                paint.pathEffect = effects
            }

            val y = vector.y.toFloat()
            path.moveTo(vector.x.toFloat(), y)
            path.lineTo(vector.x + vector.width.toFloat(), y)
            canvas.drawPath(path, paint)
        }
    }

}