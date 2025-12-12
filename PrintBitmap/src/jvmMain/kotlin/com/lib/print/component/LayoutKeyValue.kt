package com.lib.print.component

import com.lib.print.Print
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class LayoutKeyValue(
    private val rows: List<Pair<String, String>>,
    private val textSizePx: Float = 22f,
    private val gapDp: Float = 4f,
    private val lineSpacingPx: Int = 6,
    private val useMonospace: Boolean = false,
) : BasePrint(Align.LEFT) {

    private val font: Font = (Print.defaultFont ?: if (useMonospace) Font(Font.MONOSPACED, Font.PLAIN, textSizePx.toInt()) else Font("SansSerif", Font.PLAIN, textSizePx.toInt()))
        .deriveFont(textSizePx)
    private var maxLabelWidthPx = 0
    private var colonWidthPx = 0
    private var totalHeightPx = 0
    private var baselineDelta = 0f
    private val paddingPx = (4 * Print.scale).toInt()

    override fun bound(vector: Vector) {
        val graphics = DummyGraphicsHolder.graphics
        graphics.font = font
        val fm = graphics.fontMetrics

        maxLabelWidthPx = rows.maxOfOrNull { (label, _) -> fm.stringWidth(label) } ?: 0
        colonWidthPx = fm.stringWidth(" :")

        baselineDelta = fm.ascent.toFloat()
        val lineHeight = fm.height
        totalHeightPx = paddingPx + rows.size * lineHeight + (rows.size - 1) * lineSpacingPx
    }

    override fun height(): Int = totalHeightPx

    override fun draw(canvas: Graphics2D, vector: Vector) {
        if (maxLabelWidthPx == 0) bound(vector)

        canvas.font = font
        canvas.color = Color.BLACK
        val fm = canvas.fontMetrics
        val gapPx = (gapDp * Print.scale).toInt()
        val startX = vector.x
        var y = vector.y + paddingPx + baselineDelta
        val lineHeight = fm.height

        rows.forEach { (label, value) ->
            val xLabelRight = startX + maxLabelWidthPx
            val xColonLeft = xLabelRight
            val xValueLeft = xColonLeft + colonWidthPx + gapPx

            val labelWidth = fm.stringWidth(label)
            canvas.drawString(label, (xLabelRight - labelWidth).toFloat(), y)
            canvas.drawString(" :", xColonLeft.toFloat(), y)
            canvas.drawString(value, xValueLeft.toFloat(), y)

            y += (lineHeight + lineSpacingPx)
        }
    }
}

/**
 * Grafik dummy untuk mengambil FontMetrics tanpa perlu membuka window.
 */
private object DummyGraphicsHolder {
    val graphics: Graphics2D by lazy {
        val img = java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)
        img.createGraphics().apply {
            color = Color.BLACK
            font = Font("SansSerif", Font.PLAIN, 12)
        }
    }
}
