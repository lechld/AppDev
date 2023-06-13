package at.aau.edu.appdev.messenger.ui.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val drawingColor = Color.BLACK
    private val lineWidth = 15f

    private val paint = Paint().apply {
        strokeWidth = lineWidth;
        isAntiAlias = false;
        isDither = true;
        style = Paint.Style.STROKE;
        strokeJoin = Paint.Join.MITER;
        strokeCap = Paint.Cap.ROUND;
        color = drawingColor;
    }

    private val paths = mutableListOf<Path>()

    fun reset() {
        paths.clear()
        invalidate()
    }

    fun getBitmap(): Bitmap? {
        if (visibility == GONE || visibility == INVISIBLE) {
            return null
        }
        val imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(imageBitmap)

        paths.forEach { path ->
            canvas.drawPath(path, paint)
        }

        return imageBitmap
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                performClick()
                startPath(x, y)
            }

            MotionEvent.ACTION_MOVE -> {
                updatePath(x, y)
            }
        }
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun startPath(x: Float, y: Float) {
        val path = Path().apply {
            moveTo(x, y)
        }

        paths.add(path)
    }

    private fun updatePath(x: Float, y: Float) {
        val path = paths.lastOrNull() ?: return

        path.lineTo(x, y)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paths.forEach { path ->
            canvas?.drawPath(path, paint)
        }
    }
}