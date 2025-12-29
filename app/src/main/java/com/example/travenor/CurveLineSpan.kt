package com.example.travenor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.style.ReplacementSpan
import androidx.core.content.ContextCompat

class CurveLineSpan(
    context: Context,
    private val colorResId: Int
) : ReplacementSpan() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val color = ContextCompat.getColor(context, colorResId)

    override fun getSize(
        paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?
    ): Int {
        // Повертаємо ширину тексту, щоб система знала, скільки місця виділити
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas, text: CharSequence?, start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        // 1. Малюємо сам текст (помаранчевим кольором)
        paint.color = color
        canvas.drawText(text!!, start, end, x, y.toFloat(), paint)

        // 2. Налаштування для дужки
        val textWidth = paint.measureText(text, start, end)
        val strokeWidth = 8f // Товщина лінії

        // Налаштовуємо пензлик для лінії
        this.paint.color = color
        this.paint.style = Paint.Style.STROKE
        this.paint.strokeWidth = strokeWidth
        this.paint.strokeCap = Paint.Cap.ROUND // Заокруглені кінці лінії

        // 3. Малюємо дужку
        path.reset()
        // Починаємо з лівого нижнього кута тексту (трохи нижче базової лінії)
        val startX = x + 4f
        val startY = y + 18f // Опустити лінію нижче тексту

        val endX = x + textWidth - 4f
        val endY = startY

        // Контрольна точка (визначає вигин)
        val middleX = (startX + endX) / 2
        val middleY = startY - 18f // Параметр що змінює вигин

        path.moveTo(startX, startY)
        path.quadTo(middleX, middleY, endX, endY)

        canvas.drawPath(path, this.paint)
    }
}