package net.devslash.celiapp.ui.barcode

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import net.devslash.celiapp.R
import net.devslash.celiapp.camera.preview.BarcodeGraphic
import net.devslash.celiapp.camera.preview.BarcodeTrackerFactory
import net.devslash.celiapp.camera.preview.CameraSourcePreview
import net.devslash.celiapp.camera.preview.ui.GraphicOverlay

class BarcodeActivity : AppCompatActivity() {
    private val TAG = BarcodeActivity::class.java.simpleName

    private val RC_HANDLE_CAMERA_PERM: Int = 2
    private var cameraSourcePreview: CameraSourcePreview? = null
    private var cameraSource: CameraSource? = null
    private lateinit var overlay: GraphicOverlay<BarcodeGraphic>

    @SuppressLint("MissingPermission")
    override fun onCreate(icidle: Bundle?) {
        super.onCreate(icidle)
        setContentView(R.layout.barcode_capture)
        cameraSourcePreview = findViewById<CameraSourcePreview>(R.id.camera_source)
        overlay = findViewById(R.id.camera_overview)

        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
        } else {
            requestCameraPermissions()
        }

    }

    private fun createCameraSource() {
        val barcodeDetector = BarcodeDetector.Builder(this).build()


        barcodeDetector.setProcessor(MultiProcessor.Builder<Barcode>(BarcodeTrackerFactory(overlay, this)).build())

        val cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(true)
                .build()
        this.cameraSource = cameraSource
    }

    override fun onResume() {
        super.onResume()
        try {
            cameraSourcePreview?.start(cameraSource)
        } catch (e: SecurityException) {
            // unable
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.size != 0 && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
            return
        }
    }

    private fun requestCameraPermissions() {
        Log.w(TAG, "Camera permission not granted, requesting permission")
        val permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
        }

        val thisActivity: Activity = this
        val listener = View.OnClickListener { _ ->
            ActivityCompat.requestPermissions(thisActivity, permissions, RC_HANDLE_CAMERA_PERM)
        }
        val topView = findViewById<View>(R.id.topPanel)
        topView.setOnClickListener(listener)
        Snackbar.make(topView, "can i have camera", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", listener)
                .show()
    }
}
