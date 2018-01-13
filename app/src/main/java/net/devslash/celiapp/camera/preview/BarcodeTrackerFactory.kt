package net.devslash.celiapp.camera.preview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode
import net.devslash.celiapp.camera.preview.ui.GraphicOverlay

class BarcodeTrackerFactory(val graphicOverlay: GraphicOverlay<BarcodeGraphic>,
                            val context: Context) : MultiProcessor.Factory<Barcode> {

    override fun create(p0: Barcode?): Tracker<Barcode> {
        val graphic = BarcodeGraphic(graphicOverlay)
        return BarcodeGraphicTracker(graphicOverlay, graphic, context)
    }
}

class BarcodeGraphic : GraphicOverlay.Companion.Graphic {
    private var overlay: GraphicOverlay<*>
    private var textPaint: Paint
    private var barcode: Barcode? = null
    private var rectPaint: Paint

    constructor(overlay: GraphicOverlay<*>) : super(overlay) {
        this.overlay = overlay;

        textPaint = Paint()
        textPaint.color = Color.GREEN
        textPaint.textSize = 36.0f

        rectPaint = Paint()
        rectPaint.color = Color.BLUE
        rectPaint.strokeWidth = 4.0f
        rectPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        barcode?.let {
            val rect = RectF(it.boundingBox)
            rect.left = translateX(rect.left)
            rect.right = translateX(rect.right)
            rect.top = translateY(rect.top)
            rect.bottom = translateY(rect.bottom)

            canvas.drawRect(rect, rectPaint)
            canvas.drawText(it.rawValue, rect.left, rect.bottom, textPaint)
        }
    }


    fun updateItem(item: Barcode?) {
        this.barcode = item
    }

}

class BarcodeGraphicTracker(val overlay: GraphicOverlay<BarcodeGraphic>,
                            val graphic: BarcodeGraphic,
                            val context: Context): Tracker<Barcode>() {
    override fun onUpdate(p0: Detector.Detections<Barcode>?, p1: Barcode?) {
        overlay.add(graphic)
    }

    override fun onNewItem(id: Int, item: Barcode?) {
        graphic.updateItem(item)
        overlay.add(graphic)

    }

    override fun onMissing(p0: Detector.Detections<Barcode>?) {
        overlay.remove(graphic)
    }

    override fun onDone() {
        overlay.remove(graphic)
    }
}


