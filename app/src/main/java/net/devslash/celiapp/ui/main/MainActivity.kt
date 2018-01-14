package net.devslash.celiapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import dagger.android.AndroidInjection
import net.devslash.celiapp.R
import net.devslash.celiapp.SingletonUtil
import net.devslash.celiapp.ui.barcode.BarcodeActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var singleUtil: SingletonUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        Log.w("HEY", singleUtil.doSomething())
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_activity_scan_button).setOnClickListener { view ->
            val i = Intent(this, BarcodeActivity::class.java)
            startActivity(i)
        }
    }
}
