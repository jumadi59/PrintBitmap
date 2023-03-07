package com.lib.print.component

import android.graphics.Canvas


/**
 * Created by Anonim date on 24/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class LayoutFlex(private val  children: List<BasePrint>, private val  flexs: FloatArray) : BasePrint(Align.CENTER) {
    private var height = 0

    constructor() : this(emptyList(), floatArrayOf())

    fun add(print: BasePrint, flex: Float) : LayoutFlex {
        val flexs = ArrayList(flexs.asList())
        flexs.add(flex)

        return LayoutFlex(ArrayList(children).apply {
                                                  add(print)
        }, flexs.toFloatArray())
    }
    fun copy(childs: List<BasePrint>, flexs: FloatArray? = null) : LayoutFlex {
        return LayoutFlex(childs, flexs?:this.flexs)
    }

    override fun bound(vector: Vector) {
        val dy = vector.y
        var dx = vector.x
        children.forEachIndexed { index, basePrint ->
            val flex = flexs[index]
            val childWidth = (vector.width * flex).toInt()
            basePrint.bound(Vector(childWidth, vector.height, dx, dy))
            dx += childWidth
        }
    }
    override fun height(): Int {
        children.forEach {
            if (height < it.height()) height = it.height()
        }
        return height
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val dy = vector.y
        var dx = vector.x
        children.forEachIndexed { index, basePrint ->
            val flex = flexs[index]
            val childWidth = (vector.width * flex).toInt()
            basePrint.draw(canvas, Vector(childWidth, vector.height, dx, dy))
            dx += childWidth
        }
    }
}