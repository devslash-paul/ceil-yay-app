package net.devslash.celiapp.camera.preview

import android.Manifest
import android.content.Context
import android.support.annotation.RequiresPermission
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.vision.CameraSource
import android.R.attr.orientation
import android.content.res.Configuration

class CameraSourcePreview : ViewGroup {
    private var startRequested: Boolean = false
    private var cameraSource: CameraSource? = null
    private var surfaceAvailable: Boolean = false
    private var surfaceView: SurfaceView
    private var ctx: Context

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        this.surfaceView = SurfaceView(ctx)
        this.ctx = ctx

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                surfaceAvailable = false
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                surfaceAvailable = true
                try {
                    startIfReady()
                } catch (se: SecurityException) {
                    Log.e("A", "Do not have permission to start the camera")
                }
            }

        })

        addView(surfaceView)
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    fun start(cameraSource: CameraSource?) {
        if (cameraSource == null) {
            stop()
        }

        this.cameraSource = cameraSource

        if (cameraSource != null) {
            startRequested = true
            startIfReady()
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    private fun startIfReady() {
        if (startRequested && surfaceAvailable && cameraSource != null) {
            cameraSource!!.start(surfaceView.holder)

            startRequested = false
        }
    }

    private fun stop() {
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var width = 320;
        var height = 240;

        cameraSource?.let {
            it.previewSize?.let {
                width = it.width
                height = it.height
            }
        }

        if(isPortraitMode()) {
            val tmp = width
            width = height
            height = tmp
        }

        val layoutWidth = right - left
        val layoutHeight = bottom - top
        val childWidth = layoutWidth
        val childHeight = (layoutWidth / width) * height


        for (i in 0..childCount) {
            getChildAt(i)?.layout(0, 0, childWidth, childHeight)
        }

        startIfReady()

    }

    private fun isPortraitMode(): Boolean {
        val orientation = ctx.getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true
        }

        return false
    }

}