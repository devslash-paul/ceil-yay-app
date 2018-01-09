package net.devslash.celiapp.listeners

import android.support.design.widget.Snackbar
import android.view.View

object ListenerFactory {
    fun showCameraListener(layout: View) : View.OnClickListener {
        return View.OnClickListener { view ->
            Snackbar.make(layout, "Hi there", Snackbar.LENGTH_LONG).show()
        }
    }
}
