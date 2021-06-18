package com.abanoub.news.base

import com.abanoub.news.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class ParentActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // make the status bar white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    open fun isConnected(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = conManager.activeNetworkInfo
        var conn = internetInfo != null && internetInfo.isConnected
        if (!conn) {
            showMessage(R.string.offline)
        }
        return conn
    }

    open fun showDialog() {
        if (dialog != null && dialog!!.isShowing) return
        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
        } else {
            dialog = ProgressDialog.show(this, "", getString(R.string.loading))
            dialog!!.setCancelable(true)
        }
    }

    open fun showMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    open fun showMessage(msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun hideSoftKeyboard() {
        try {
            val inputMethodManager: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    0
                )
            } //if
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun hideDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}

