package com.lib.print.component

import android.graphics.Canvas


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
abstract class BasePrint(val align: Align = Align.LEFT) {

    abstract fun height() : Int

    open fun bound(vector: Vector) {

    }

    abstract fun draw(canvas: Canvas, vector: Vector)
}

enum class Align {
    LEFT, CENTER, RIGHT
}

class Vector(var width :Int = 0,var height :Int = 0,var x :Int = 0, var  y :Int = 0)