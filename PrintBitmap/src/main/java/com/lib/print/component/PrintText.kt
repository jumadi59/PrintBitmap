package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
open class PrintText(protected val text: String, protected val fontSize: Int, align: Align = Align.LEFT, val fontStyle: FontStyle = FontStyle.NORMAL) : BasePrint(align) {

    protected val padding = 8
    protected val paint = TextPaint()

    constructor(text: String,fontSize: FontSize = FontSize.NORMAL, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) : this(text, fontSize.size, align, fontStyle)

    constructor(text: String) : this(text, FontSize.NORMAL)

    init {
        setStyle(fontStyle)
    }

    fun fontSize(fontSize: Int) : PrintText {
        return copy(text, fontSize)
    }
    fun align(align: Align) : PrintText {
        return copy(text, align = align)
    }
    fun fontStyle(fontStyle: FontStyle) : PrintText {
        return copy(text, fontStyle = fontStyle)
    }

    protected fun setStyle(style: FontStyle) {
        paint.isFakeBoldText = style == FontStyle.BOLD
        paint.textSkewX = if (style == FontStyle.ITALIC) -0.25f else 0f
    }

    fun copy(text: String,fontSize: Int? = null, align: Align? = null, fontStyle: FontStyle? = null) : PrintText {
        return PrintText(text, fontSize?:this.fontSize, align?:this.align, fontStyle?:this.fontStyle)
    }

    override fun height(): Int {
        val bound = Rect()
        paint.textSize = fontSize.toFloat()
        paint.getTextBounds(text, 0, text.length, bound)

        return bound.height() + padding
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val paint = TextPaint()
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

enum class FontSize(val size: Int) {
    SMALL(16),  NORMAL(20), LARGE(30)
}