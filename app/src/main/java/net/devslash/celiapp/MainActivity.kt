package net.devslash.celiapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import net.devslash.celiapp.listeners.ListenerFactory

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var cameraButton = findViewById(R.id.main_activity_scan_button) as Button
        cameraButton.setOnClickListener(ListenerFactory.showCameraListener(findViewById(R.id.main_activity_constraint)))

    }
}
