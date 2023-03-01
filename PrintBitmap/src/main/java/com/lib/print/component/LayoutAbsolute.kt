package com.lib.print.component

import android.graphics.Canvas
import android.util.Log


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class LayoutAbsolute(private val childs: List<BasePrint>) : BasePrint(Align.LEFT) {

    constructor() : this(emptyList())

    fun add(print: BasePrint) : LayoutAbsolute {
        return LayoutAbsolute(ArrayList(childs).apply {
            add(print)
        })
    }

    private var height = 0
    override fun height(): Int {
        childs.forEach {
            if (height < it.height()) height = it.height()
        }
        return height
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val dy = vector.y
        childs.forEach {
            it.draw(canvas, Vector(vector.width, vector.height, vector.x, dy))
        }
    }
}