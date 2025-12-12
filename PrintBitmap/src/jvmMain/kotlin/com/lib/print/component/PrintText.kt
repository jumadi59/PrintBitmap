package com.lib.print.component

import com.lib.print.Print
import java.awt.Font
import java.awt.Graphics2D
import java.awt.font.FontRenderContext
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.font.TextLayout
import java.text.AttributedCharacterIterator
import java.text.AttributedString

open class PrintText(
    protected val text: String,
    protected val fontSize: Int,
    align: Align = Align.LEFT,
    val fontStyle: FontStyle = FontStyle.NORMAL
) : BasePrint(align) {

    protected val padding = (8 * Print.scale).toInt()
    private var layouts: List<TextLayout> = emptyList()
    private var layoutHeight: Int = 0

    constructor(text: String, fontSize: FontSize = FontSize.NORMAL, align: Align = Align.LEFT, fontStyle: FontStyle = FontStyle.NORMAL) :
        this(text, fontSize.size, align, fontStyle)

    constructor(text: String) : this(text, FontSize.NORMAL)

    private fun fontForStyle(): Font {
        val base = (Print.defaultFont ?: Font(Font.MONOSPACED, Font.PLAIN, fontSize)).deriveFont(fontSize.toFloat())
        val style = when (fontStyle) {
            FontStyle.BOLD -> Font.BOLD
            FontStyle.ITALIC -> Font.ITALIC
            FontStyle.NORMAL -> Font.PLAIN
        }
        return base.deriveFont(style.toFloat())
    }

    fun fontSize(fontSize: Int): PrintText = copy(text, fontSize)
    fun align(align: Align): PrintText = copy(text, align = align)
    fun fontStyle(fontStyle: FontStyle): PrintText = copy(text, fontStyle = fontStyle)

    fun copy(text: String, fontSize: Int? = null, align: Align? = null, fontStyle: FontStyle? = null): PrintText {
        return PrintText(text, fontSize ?: this.fontSize, align ?: this.align, fontStyle ?: this.fontStyle)
    }

    override fun height(): Int = layoutHeight + padding

    override fun bound(vector: Vector) {
        val font = fontForStyle()
        val frc = FontRenderContext(null, true, true)
        val attributed = AttributedString(text)
        attributed.addAttribute(TextAttribute.FONT, font)
        val iterator: AttributedCharacterIterator = attributed.iterator
        val measurer = LineBreakMeasurer(iterator, frc)

        val localLayouts = mutableListOf<TextLayout>()
        var heightAccumulator = 0
        while (measurer.position < iterator.endIndex) {
            val layout = measurer.nextLayout(vector.width.toFloat())
            localLayouts.add(layout)
            heightAccumulator += (layout.ascent + layout.descent + layout.leading).toInt()
        }
        layouts = localLayouts
        layoutHeight = heightAccumulator
    }

    override fun draw(canvas: Graphics2D, vector: Vector) {
        val dx: Int = when (align) {
            Align.CENTER -> vector.x + (vector.width / 2)
            Align.RIGHT -> vector.x + vector.width
            else -> vector.x
        }

        var yCursor = vector.y + padding
        layouts.forEach { layout ->
            yCursor += layout.ascent.toInt()
            layout.draw(canvas, dx.toFloat(), yCursor.toFloat())
            yCursor += (layout.descent + layout.leading).toInt()
        }
    }
}

enum class FontSize(val size: Int) {
    SMALL(16), NORMAL(20), LARGE(30)
}

enum class FontStyle {
    BOLD, ITALIC, NORMAL,
}
