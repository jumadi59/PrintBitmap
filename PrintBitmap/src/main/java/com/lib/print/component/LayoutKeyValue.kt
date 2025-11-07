package com.lib.print.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.lib.print.Print

/** Render pasangan "name : value" dengan kolom terjaga sejajar. */
class LayoutKeyValue(
    private val rows: List<Pair<String, String>>,
    private val textSizePx: Float = 22f,       // sesuaikan
    private val gapDp: Float = 4f,             // jarak setelah ":" dalam dp
    private val lineSpacingPx: Int = 6,        // jarak antar baris (px)
    private val useMonospace: Boolean = false, // true bila ingin pakai Typeface.MONOSPACE
) : BasePrint(Align.LEFT) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isDither = true
        isFilterBitmap = true
        textSize = textSizePx
        color = Color.BLACK
        if (useMonospace) typeface = Typeface.MONOSPACE
    }

    private var maxLabelWidthPx = 0
    private var colonWidthPx = 0
    private var totalHeightPx = 0
    private var baselineDelta = 0f
    private val paddingPx = (4 * Print.scale).toInt()

    override fun bound(vector: Vector) {
        // Hitung kolom label terlebar
        maxLabelWidthPx = rows.maxOfOrNull { (label, _) -> paint.measureText(label).toInt() } ?: 0
        colonWidthPx = paint.measureText(" :").toInt() // spasi + ":" supaya rapih

        // Hitung metrik teks (untuk baseline)
        val fm = paint.fontMetrics
        val lineHeight = (fm.descent - fm.ascent).toInt()
        baselineDelta = -fm.ascent

        totalHeightPx = paddingPx + rows.size * lineHeight + (rows.size - 1) * lineSpacingPx
    }

    override fun height(): Int = totalHeightPx

    override fun draw(canvas: Canvas, vector: Vector) {
        // Pastikan sudah dibound; jika belum, lakukan kalkulasi minimal
        if (maxLabelWidthPx == 0) bound(vector)

        val gapPx = (gapDp * Print.scale).toInt()
        val startX = vector.x
        var y = vector.y + paddingPx + baselineDelta

        val fm = paint.fontMetrics
        val lineHeight = (fm.descent - fm.ascent).toInt()

        rows.forEach { (label, value) ->
            // x posisi kolom
            val xLabelRight = startX + maxLabelWidthPx
            val xColonLeft  = xLabelRight
            val xValueLeft  = xColonLeft + colonWidthPx + gapPx

            // Gambar label rata-kanan ke xLabelRight
            val labelWidth = paint.measureText(label)
            canvas.drawText(label, xLabelRight - labelWidth, y, paint)

            // Gambar " :" tepat di kolom titik dua
            canvas.drawText(" :", xColonLeft.toFloat(), y, paint)

            // Gambar value mulai dari kolom value
            canvas.drawText(value, xValueLeft.toFloat(), y, paint)

            y += lineHeight + lineSpacingPx
        }
    }
}
