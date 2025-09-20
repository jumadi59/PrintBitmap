package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.lib.print.Print
import androidx.core.graphics.withTranslation


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
open class PrintText(protected val text: String, protected val fontSize: Int, align: Align = Align.LEFT, val fontStyle: FontStyle = FontStyle.NORMAL) : BasePrint(align) {

    protected val padding = (8 * Print.scale).toInt()
    protected val paint = TextPaint()
    private lateinit var textLayout: StaticLayout

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
        paint.textSize = (fontSize * Print.scale)
        paint.isFakeBoldText = style == FontStyle.BOLD
        paint.textSkewX = if (style == FontStyle.ITALIC) -0.25f else 0f
        paint.textAlign = when(align) {
            Align.CENTER -> Paint.Align.CENTER
            Align.RIGHT -> Paint.Align.RIGHT
            else -> Paint.Align.LEFT
        }
    }

    fun copy(text: String,fontSize: Int? = null, align: Align? = null, fontStyle: FontStyle? = null) : PrintText {
        return PrintText(text, fontSize?:this.fontSize, align?:this.align, fontStyle?:this.fontStyle)
    }

    override fun height(): Int {
        return textLayout.height + padding
    }

    override fun bound(vector: Vector) {
        textLayout = StaticLayout(text, paint, vector.width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
    }

    override fun draw(canvas: Canvas, vector: Vector) {

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

        canvas.withTranslation(dx.toFloat(), vector.y.toFloat()) {
            textLayout.draw(this)
        }
    }
}

enum class FontSize(val size: Int) {
    SMALL(16),  NORMAL(20), LARGE(30)
}