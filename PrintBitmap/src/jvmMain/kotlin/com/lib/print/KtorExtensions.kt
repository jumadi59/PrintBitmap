package com.lib.print

import io.ktor.http.ContentType
import io.ktor.http.content.ByteArrayContent

fun Print.buildPngContent(paperWidth: Int = 400): ByteArrayContent {
    val image = build(paperWidth)
    val png = image.asPng()
    return ByteArrayContent(png, contentType = ContentType.Image.PNG)
}
