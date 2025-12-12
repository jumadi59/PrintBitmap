package com.lib.print.component

import java.awt.Graphics2D

class LayoutAbsolute(private val children: List<BasePrint>) : BasePrint(Align.LEFT) {

    constructor() : this(emptyList())

    fun add(print: BasePrint): LayoutAbsolute {
        return LayoutAbsolute(ArrayList(children).apply { add(print) })
    }

    override fun bound(vector: Vector) {
        children.forEach { it.bound(vector) }
    }

    private var layoutHeight = 0
    override fun height(): Int {
        children.forEach {
            if (layoutHeight < it.height()) layoutHeight = it.height()
        }
        return layoutHeight
    }

    override fun draw(canvas: Graphics2D, vector: Vector) {
        val dy = vector.y
        children.forEach {
            it.draw(canvas, Vector(vector.width, vector.height, vector.x, dy))
        }
    }
}
