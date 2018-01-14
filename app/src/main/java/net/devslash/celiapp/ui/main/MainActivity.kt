package net.devslash.celiapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.devslash.celiapp.R
import net.devslash.celiapp.SingletonUtil
import net.devslash.celiapp.barcode.net.BarcodeDao
import net.devslash.celiapp.ui.barcode.BarcodeActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var barcodeDao: BarcodeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_activity_scan_button).setOnClickListener { view ->
            val i = Intent(this, BarcodeActivity::class.java)
            startActivity(i)
        }

        try {
            barcodeDao.getForBarcode("abc")
                    .doOnError { e ->
                        Log.w("TAG", "there was an error")
                    }
                    .doOnNext { e ->
                        Log.w("TAG", "sdf")
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        } catch (e: Exception) {
            Log.w("TAG", "Or not")
        }
    }
}
