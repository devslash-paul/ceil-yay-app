package net.devslash.celiapp.listeners

import android.view.View
import android.widget.Toast

object ListenerFactory {
    fun showCameraListener(): View.OnClickListener {
        return View.OnClickListener { view ->
            Toast.makeText(view.context, "Hi there", Toast.LENGTH_LONG).show()
        }
    }
}
