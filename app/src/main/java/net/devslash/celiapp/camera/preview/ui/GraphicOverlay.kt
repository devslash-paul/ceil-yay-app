package net.devslash.celiapp.camera.preview.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.google.android.gms.vision.CameraSource

class GraphicOverlay<T : GraphicOverlay.Companion.Graphic>(ctx: Context, attributeSet: AttributeSet) :
        View(ctx, attributeSet) {

    private var widthScaleFactor = 1.0f
    private var heightScaleFactor = 1.0f
    private var facing = CameraSource.CAMERA_FACING_BACK
    private val graphics = HashSet<T>()

    private var previewWidth: Int = 0
    private var previewHeight: Int = 0


    /**
     * Base class for a custom graphics object to be rendered within the overlay
     */
    companion object {
        abstract class Graphic(val graphicOverlay: GraphicOverlay<*>) {
            abstract fun draw(canvas: Canvas);

            fun scaleX(horizontal: Float): Float {
                return horizontal * graphicOverlay.widthScaleFactor
            }

            fun scaleY(vertical: Float): Float {
                return vertical * graphicOverlay.heightScaleFactor
            }

            fun translateX(x: Float): Float {
                if (graphicOverlay.facing == CameraSource.CAMERA_FACING_FRONT) {
                    return graphicOverlay.getWidth() - scaleX(x)
                } else {
                    return scaleX(x)
                }
            }

            fun translateY(y: Float): Float {
                return scaleY(y)
            }

            fun postInvalidate() {
                graphicOverlay.postInvalidate()
            }
        }
    }

    fun clear() {
        synchronized(this, {
            graphics.clear()
        })
        postInvalidate()
    }

    fun add(graphic: T) {
        synchronized(this, {
            graphics.add(graphic)
        })
        postInvalidate()
    }

    fun remove(graphic: T) {
        synchronized(this, {
            graphics.remove(graphic)
        })
        postInvalidate()
    }

    fun getGraphics(): List<T> {
        synchronized(this, {
            return ArrayList(graphics)
        })
    }

    fun setCameraInfo(previewWidth: Int, previewHeight: Int, facing: Int) {
        synchronized(this, {
            this.previewWidth = previewWidth
            this.previewHeight = previewHeight
            this.facing = facing
        })
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            synchronized(this, {
                if ((previewWidth != 0) && (previewHeight != 0)) {
                    widthScaleFactor = canvas.width.toFloat() / previewWidth.toFloat()
                    heightScaleFactor = canvas.height.toFloat() / previewHeight.toFloat()
                }

                for (g in graphics) {
                    g.draw(canvas)
                }
            })
        }
    }
}

