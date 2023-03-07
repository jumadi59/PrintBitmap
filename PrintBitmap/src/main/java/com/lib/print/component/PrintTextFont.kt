package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import com.lib.print.Print


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class PrintTextFont(val typeface: Typeface, val text: String, val fontSize: Int, align: Align = Align.LEFT, val fontStyle: FontStyle = FontStyle.NORMAL) : BasePrint(align) {

    protected val padding = (8 * Print.scale).toInt()
    protected val paint = TextPaint()
    private lateinit var textLayout: StaticLayout

    constructor(typeface: Typeface, text: String,fontSize: FontSize = FontSize.NORMAL, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) : this(typeface, text, fontSize.size, align, fontStyle)

    init {
        paint.typeface = typeface
        setStyle(fontStyle)
    }

    fun copy(text: String,typeface: Typeface? = null, fontSize: Int? = null, align: Align? = null, fontStyle: FontStyle? = null) : PrintTextFont {
        return PrintTextFont(typeface?:paint.typeface!!, text, fontSize?:this.fontSize, align?:this.align, fontStyle?:this.fontStyle)
    }

    protected fun setStyle(style: FontStyle) {
        paint.textSize = (fontSize * Print.scale)
        paint.isFakeBoldText = style == FontStyle.BOLD
        paint.textSkewX = if (style == FontStyle.ITALIC) -0.25f else 0f
        paint.textAlign = when(align) {
            Align.CENTER -> Paint.Align.CENTER
            Align.RIGHT -> Paint.Align.RIGHT
            else -> Paint.Align.LEFT
        }
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

    override fun bound(vector: Vector) {
        textLayout = StaticLayout(text, paint, vector.width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
    }

    override fun height(): Int {
        return textLayout.height + padding
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        setStyle(fontStyle)

        val dx: Int = when(align) {
            Align.CENTER -> {
                vector.x + (vector.width / 2)
            }
            Align.RIGHT -> {
                vector.x + vector.width
            }
            else -> {
                vector.x
            }
        }

        canvas.save()
        canvas.translate(dx.toFloat(), vector.y.toFloat())
        textLayout.draw(canvas)
        canvas.restore()
    }
}

enum class FontStyle {
    BOLD, ITALIC, NORMAL
}