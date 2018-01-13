package net.devslash.celiapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import net.devslash.celiapp.barcode.activities.BarcodeActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var singleUtil: SingletonUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("HEY", singleUtil.doSomething())
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_activity_scan_button).setOnClickListener { view ->
            val i = Intent(this, BarcodeActivity::class.java)
            startActivity(i)
        }
    }
}
