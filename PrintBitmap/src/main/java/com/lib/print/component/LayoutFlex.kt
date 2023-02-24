package com.lib.print.component

import android.graphics.Canvas
import com.lib.print.component.Align
import com.lib.print.component.BasePrint
import com.lib.print.component.Vector


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class LayoutFlex(private val  childs: List<BasePrint>, private val  flexs: FloatArray) : BasePrint(Align.CENTER) {
    private var height = 0
    override fun height(): Int {
        childs.forEach {
            if (height < it.height()) height = it.height()
        }
        return height
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val dy = vector.y
        var dx = vector.x
        childs.forEachIndexed { index, basePrint ->
            val flex = flexs[index]
            basePrint.draw(canvas, Vector(vector.width, vector.height, dx, dy))
            dx += (vector.width * flex).toInt()
        }
    }
}