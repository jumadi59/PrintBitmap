package com.lib.print.component

import android.graphics.Canvas


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class LayoutFlex(
    private val children: List<BasePrint>,
    private val flexes: FloatArray
) : BasePrint(Align.CENTER) {

    constructor() : this(emptyList(), floatArrayOf())

    /** Tambah child + flex; kelas tetap imut (return instans baru). */
    fun add(print: BasePrint, flex: Float): LayoutFlex {
        require(flex >= 0f) { "Flex harus >= 0" }
        val newChildren = ArrayList(children).apply { add(print) }
        val newFlexes = FloatArray(flexes.size + 1).apply {
            if (flexes.isNotEmpty()) System.arraycopy(flexes, 0, this, 0, flexes.size)
            this[lastIndex] = flex
        }
        return LayoutFlex(newChildren, newFlexes)
    }

    /** Versi 'copy' yang aman. */
    fun withChildren(newChildren: List<BasePrint>, newFlexes: FloatArray? = null): LayoutFlex {
        return LayoutFlex(newChildren, newFlexes ?: this.flexes)
    }

    override fun bound(vector: Vector) {
        if (children.isEmpty()) return
        require(children.size == flexes.size) {
            "Jumlah children (${children.size}) harus sama dengan jumlah flexes (${flexes.size})."
        }

        val (weights, totalWidth) = normalizedWeightsAndWidth(vector.width)
        var dx = vector.x
        val dy = vector.y

        // Hitung width per child dengan distribusi sisa pixel ke anak terakhir
        var accumulated = 0
        children.forEachIndexed { i, child ->
            val isLast = i == children.lastIndex
            val w = if (!isLast) {
                val cw = (totalWidth * weights[i]).toInt()
                accumulated += cw
                cw
            } else {
                // Pastikan jumlah lebar == totalWidth (atasi pembulatan)
                totalWidth - accumulated
            }
            child.bound(Vector(w, vector.height, dx, dy))
            dx += w
        }
    }

    override fun height(): Int {
        if (children.isEmpty()) return 0
        // Ambil tinggi maksimum dari anak-anak
        var maxH = 0
        children.forEach { child ->
            val h = child.height()
            if (h > maxH) maxH = h
        }
        return maxH
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        if (children.isEmpty()) return
        require(children.size == flexes.size) {
            "Jumlah children (${children.size}) harus sama dengan jumlah flexes (${flexes.size})."
        }

        val (weights, totalWidth) = normalizedWeightsAndWidth(vector.width)
        var dx = vector.x
        val dy = vector.y

        var accumulated = 0
        children.forEachIndexed { i, child ->
            val isLast = i == children.lastIndex
            val w = if (!isLast) {
                val cw = (totalWidth * weights[i]).toInt()
                accumulated += cw
                cw
            } else {
                totalWidth - accumulated
            }
            child.draw(canvas, Vector(w, vector.height, dx, dy))
            dx += w
        }
    }

    /**
     * Normalisasi bobot flex:
     * - Jika sum == 0, semua anak diberi bobot sama
     * - Jika sum != 1, bobot dinormalisasi agar total 1
     */
    private fun normalizedWeightsAndWidth(totalWidth: Int): Pair<FloatArray, Int> {
        val count = children.size
        val weights = FloatArray(count)
        val sum = flexes.sum()

        if (sum <= 0f) {
            // Beri bobot sama rata
            val eq = 1f / count
            for (i in 0 until count) weights[i] = eq
        } else {
            // Normalisasi
            for (i in 0 until count) {
                val v = flexes[i]
                require(v >= 0f) { "Flex ke-$i harus >= 0" }
                weights[i] = v / sum
            }
        }
        return weights to totalWidth
    }
}
