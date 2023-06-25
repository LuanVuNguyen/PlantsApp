package com.example.plantsapp.custom;

import android.view.View;
import android.widget.ProgressBar;

public class ProgressBarManager {
    private ProgressBar progressBar;

    public ProgressBarManager(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}

