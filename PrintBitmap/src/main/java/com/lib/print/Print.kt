package com.lib.print

import android.graphics.*
import com.lib.print.component.Align
import com.lib.print.component.BasePrint
import com.lib.print.component.Vector


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class Print {

    private val items = ArrayList<BasePrint>()
    private var vector = Vector()

    fun build(paperWidth: Int = 400) : Bitmap {
        vector.width = paperWidth
        getHeight()
        val bitmap = Bitmap.createBitmap(paperWidth, vector.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        draw(canvas)
        return bitmap
    }

    fun list() : List<BasePrint> = items

    fun  draw(canvas: Canvas) {
        items.forEach {
            it.draw(canvas, vector)
            vector.y += it.height()
            canvas.save()
        }
    }

    fun add(printItem: BasePrint) : Print {
        items.add(printItem)
        return this
    }

    fun feed(size: Int = 12) : Print {
        items.add(Feed(size))
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


    private fun getHeight() {
        vector.height = 0
        items.forEach {
            vector.height += it.height()
        }
    }

    class Feed(private val size: Int = 12) : BasePrint(Align.CENTER) {
        override fun height(): Int = size

        override fun draw(canvas: Canvas, vector: Vector) {}
    }

    class DoubleLine(private val isDash: Boolean = false) : BasePrint(Align.CENTER) {
        private val strokeWidth = 2f
        private var padding = (16 - strokeWidth) / 2

        override fun height(): Int = (strokeWidth + (padding * 2)).toInt()

        override fun draw(canvas: Canvas, vector: Vector) {
            singleLine(canvas, vector)
            canvas.save()
            singleLine(canvas, Vector(vector.width, vector.height, vector.x, vector.y + (padding.toInt() /2)))
        }

        private fun singleLine(canvas: Canvas, vector: Vector) {
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth
            paint.color = Color.BLACK
            val path = Path()

            if (isDash) {
                val effects = DashPathEffect(floatArrayOf(4f, 2f, 4f, 2f), 0f)
                paint.pathEffect = effects
            }

            val y = vector.y - padding
            path.moveTo(0f, y)
            path.lineTo(vector.width.toFloat(), y)
            canvas.drawPath(path, paint)
        }
    }

    class SingleLine(private val isDash: Boolean = false) : BasePrint(Align.CENTER) {
        private val strokeWidth = 2f
        private var padding = (16 - strokeWidth) / 2

            override fun height(): Int = (strokeWidth + (padding * 2)).toInt()

        override fun draw(canvas: Canvas, vector: Vector) {
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth
            paint.color = Color.BLACK
            val path = Path()

            if (isDash) {
                val effects = DashPathEffect(floatArrayOf(4f, 2f, 4f, 2f), 0f)
                paint.pathEffect = effects
            }

            val y = vector.y - padding
            path.moveTo(0f, y)
            path.lineTo(vector.width.toFloat(), y)
            canvas.drawPath(path, paint)
        }
    }

}