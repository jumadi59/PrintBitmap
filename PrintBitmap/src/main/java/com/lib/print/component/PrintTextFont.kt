package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class PrintTextFont(val typeface: Typeface, val text: String, val fontSize: Int, align: Align = Align.LEFT, val fontStyle: FontStyle = FontStyle.NORMAL) : BasePrint() {

    protected val padding = 8
    protected val paint = TextPaint()

    constructor(typeface: Typeface, text: String,fontSize: FontSize = FontSize.NORMAL, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) : this(typeface, text, fontSize.size, align, fontStyle)

    init {
        paint.typeface = typeface
        setStyle(fontStyle)
    }

    fun copy(text: String,typeface: Typeface? = null, fontSize: Int? = null, align: Align? = null, fontStyle: FontStyle? = null) : PrintTextFont {
        return PrintTextFont(typeface?:paint.typeface!!, text, fontSize?:this.fontSize, align?:this.align, fontStyle?:this.fontStyle)
    }

    protected fun setStyle(style: FontStyle) {
        paint.isFakeBoldText = style == FontStyle.BOLD
        paint.textSkewX = if (style == FontStyle.ITALIC) -0.25f else 0f
    }

    fun typeface(typeface: Typeface?) :PrintTextFont {
        return copy(text, typeface)
    }

    fun fontSize(fontSize: Int) : PrintTextFont {
        return copy(text, typeface, fontSize)
    }
    fun align(align: Align) : PrintTextFont {
        return copy(text, align = align)
    }
    fun fontStyle(fontStyle: FontStyle) : PrintTextFont {
        return copy(text, fontStyle = fontStyle)
    }

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