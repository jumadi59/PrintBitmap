package com.lib.print

import com.lib.print.component.Align
import com.lib.print.component.BasePrint
import com.lib.print.component.Vector
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.image.BufferedImage

class Print : BasePrint(Align.CENTER) {

    companion object {
        const val FEED_SIZE = 12
        var scale = 1f
        var defaultFont: Font? = Font(Font.MONOSPACED, Font.PLAIN, 12)
    }

    private val items = ArrayList<BasePrint>()
    private var vector = Vector()

    fun config(scale: Float = 1f, defaultFont: Font? = null): Print {
        Print.scale = scale
        Print.defaultFont = defaultFont ?: Print.defaultFont
        return this
    }

    fun build(paperWidth: Int = 400): BufferedImage {
        vector.width = (paperWidth * scale).toInt()
        bound(vector)
        height()
        val bitmap = BufferedImage(vector.width, vector.height, BufferedImage.TYPE_INT_ARGB)
        val canvas = bitmap.createGraphics()
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        canvas.color = Color.WHITE
        canvas.fillRect(0, 0, vector.width, vector.height)
        draw(canvas, vector)
        canvas.dispose()
        return bitmap
    }

    override fun draw(canvas: Graphics2D, vector: Vector) {
        items.forEach {
            it.draw(canvas, vector)
            vector.y += it.height()
        }
    }

    fun build(): BufferedImage = build(400)

    fun list(): List<BasePrint> = items

    fun add(printItem: BasePrint): Print {
        items.add(printItem)
        return this
    }

    fun feed(size: Int = FEED_SIZE): Print {
        items.add(Feed((size * scale).toInt()))
        return this
    }

    fun feed(): Print {
        items.add(Feed(FEED_SIZE))
        return this
    }

    fun singleLine(isDash: Boolean = false): Print {
        items.add(SingleLine(isDash))
        return this
    }

    fun doubleLineLine(isDash: Boolean = false): Print {
        items.add(DoubleLine(isDash))
        return this
    }

    override fun bound(vector: Vector) {
        items.forEach { it.bound(vector) }
    }

    override fun height(): Int {
        vector.height = 0
        items.forEach { vector.height += it.height() }
        return vector.height
    }

    class Feed(private val size: Int = FEED_SIZE) : BasePrint(Align.CENTER) {
        override fun height(): Int = size
        override fun draw(canvas: Graphics2D, vector: Vector) {}
    }

    class DoubleLine(private val isDash: Boolean = false) : BasePrint(Align.CENTER) {
        private val strokeWidth = 2f * scale
        private var padding = (((FEED_SIZE * 2) * scale) - strokeWidth) / 2

        override fun height(): Int = (strokeWidth + (padding * 2)).toInt()

        override fun draw(canvas: Graphics2D, vector: Vector) {
            singleLine(canvas, Vector(vector.width, vector.height, vector.x, vector.y))
            singleLine(canvas, Vector(vector.width, vector.height, vector.x, vector.y + (padding.toInt() / 2)))
        }

        private fun singleLine(canvas: Graphics2D, vector: Vector) {
            val stroke: Stroke = if (isDash) {
                BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(6f, 4f, 6f, 4f), 0f)
            } else {
                BasicStroke(strokeWidth)
            }
            val oldStroke = canvas.stroke
            canvas.color = Color.BLACK
            canvas.stroke = stroke
            val y = vector.y.toFloat()
            canvas.drawLine(vector.x, y.toInt(), vector.x + vector.width, y.toInt())
            canvas.stroke = oldStroke
        }
    }

    class SingleLine(private val isDash: Boolean = false) : BasePrint(Align.CENTER) {
        private val strokeWidth = 2f * scale
        private var padding = ((FEED_SIZE * scale) - strokeWidth) / 2

        override fun height(): Int = (strokeWidth + (padding * 2)).toInt()

        override fun draw(canvas: Graphics2D, vector: Vector) {
            val stroke: Stroke = if (isDash) {
                BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(6f, 4f, 6f, 4f), 0f)
            } else {
                BasicStroke(strokeWidth)
            }
            val oldStroke = canvas.stroke
            canvas.color = Color.BLACK
            canvas.stroke = stroke
            val y = vector.y.toFloat()
            canvas.drawLine(vector.x, y.toInt(), vector.x + vector.width, y.toInt())
            canvas.stroke = oldStroke
        }
    }
}
