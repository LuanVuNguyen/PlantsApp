package com.example.plantsapp.custom

import android.view.View
import android.widget.ProgressBar

class ProgressBarManager(private val progressBar: ProgressBar?) {
    fun showProgressBar() {
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun hideProgressBar() {
        if (progressBar != null) {
            progressBar.visibility = View.GONE
        }
    }
}