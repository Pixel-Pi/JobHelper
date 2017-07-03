package com.example.marc.jobhelper.Controller;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.marc.jobhelper.Model.Company;

import java.lang.ref.WeakReference;

/**
 * Created by marc on 02.07.17.
 */

public class EditCompanyImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<CollapsingToolbarLayout> toolbarReference;
    private final WeakReference<ProgressBar> loadingIndicatorReference;
    private Company company;

    public EditCompanyImageLoaderTask(ImageView imageView, CollapsingToolbarLayout toolbar, ProgressBar loadingIndicator, Company company) {
        imageViewReference = new WeakReference<>(imageView);
        toolbarReference = new WeakReference<>(toolbar);
        loadingIndicatorReference = new WeakReference<>(loadingIndicator);
        this.company = company;
    }

    @Override
    protected void onPreExecute() {
        if (loadingIndicatorReference != null) {
            ProgressBar progInd = loadingIndicatorReference.get();
            if (progInd != null) {
                progInd.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = company.loadBitmap();
        int R = 0; int G = 0; int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        int conColor = Color.rgb(1 - (R / n), 1 - (G / n) , 1 - (B / n));
        company.setContrastColor(conColor);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (loadingIndicatorReference != null) {
            ProgressBar progInd = loadingIndicatorReference.get();
            if (progInd != null) {
                progInd.setVisibility(View.GONE);
            }
        }
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        if (toolbarReference != null) {
            CollapsingToolbarLayout toolbar = toolbarReference.get();
            if (toolbar != null) {
                toolbar.setExpandedTitleColor(company.getContrastColor());
            }
        }
    }
}

