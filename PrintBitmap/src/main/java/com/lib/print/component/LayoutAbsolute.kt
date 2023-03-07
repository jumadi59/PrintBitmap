package com.lib.print.component

import android.graphics.Canvas


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class LayoutAbsolute(private val children: List<BasePrint>) : BasePrint(Align.LEFT) {

    constructor() : this(emptyList())

    fun add(print: BasePrint) : LayoutAbsolute {
        return LayoutAbsolute(ArrayList(children).apply {
            add(print)
        })
    }

    override fun bound(vector: Vector) {
        children.forEach {
            it.bound(vector)
        }
    }

    private var height = 0
    override fun height(): Int {
        children.forEach {
            if (height < it.height()) height = it.height()
        }
        return height
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val dy = vector.y
        children.forEach {
            it.draw(canvas, Vector(vector.width, vector.height, vector.x, dy))
        }
    }
}