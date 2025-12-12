package com.lib.print.component

import java.awt.Graphics2D

abstract class BasePrint(val align: Align = Align.LEFT) {

    abstract fun height(): Int

    open fun bound(vector: Vector) {}

    abstract fun draw(canvas: Graphics2D, vector: Vector)
}

enum class Align {
    LEFT, CENTER, RIGHT
}

class Vector(var width: Int = 0, var height: Int = 0, var x: Int = 0, var y: Int = 0)
