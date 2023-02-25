package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class PrintTextFont : PrintText {

    constructor(typeface: Typeface, text: String, fontSize: Int, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) : super (text, fontSize, align, fontStyle) {
        paint.typeface = typeface
        setStyle(fontStyle)
    }

    constructor(typeface: Typeface, text: String,fontSize: FontSize = FontSize.NORMAL, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) : this(typeface, text, fontSize.size, align, fontStyle)


    override fun height(): Int {
        val bound = Rect()
        paint.textSize = fontSize.toFloat()
        paint.getTextBounds(text, 0, text.length, bound)

        return bound.height() + padding
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        setStyle(fontStyle)
        val dx: Int

        when(align) {
            Align.CENTER -> {
                dx = vector.x + (vector.width / 2)
                paint.textAlign = Paint.Align.CENTER
            }
            Align.RIGHT -> {
                dx = vector.width
                paint.textAlign = Paint.Align.RIGHT
            }
            else -> {
                dx = vector.x
                paint.textAlign = Paint.Align.LEFT
            }
        }

        paint.textSize = fontSize.toFloat()
        canvas.drawText(text, dx.toFloat(), vector.y.toFloat(), paint)
    }
}

enum class FontStyle {
    BOLD, ITALIC, NORMAL
}