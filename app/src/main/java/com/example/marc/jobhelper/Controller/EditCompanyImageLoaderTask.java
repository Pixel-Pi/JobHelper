package com.example.marc.jobhelper.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.marc.jobhelper.Model.Company;

import java.lang.ref.WeakReference;

/**
 * Ein Task für das asynchrone Laden von Bildern im EditCompany Screen.
 * Created by marc on 02.07.17.
 */

class EditCompanyImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<CollapsingToolbarLayout> toolbarReference;
    private final WeakReference<ProgressBar> loadingIndicatorReference;
    private Company company;

    EditCompanyImageLoaderTask(ImageView imageView, CollapsingToolbarLayout toolbar, ProgressBar loadingIndicator, Company company) {
        imageViewReference = new WeakReference<>(imageView);
        toolbarReference = new WeakReference<>(toolbar);
        loadingIndicatorReference = new WeakReference<>(loadingIndicator);
        this.company = company;
    }

    @Override
    protected void onPreExecute() {
        ProgressBar progInd = loadingIndicatorReference.get();
        if (progInd != null) {
            progInd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = company.loadBitmap();
        Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap, 500, 500);

        int R = 0; int G = 0; int B = 0;
        int height = resized.getHeight();
        int width = resized.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        resized.getPixels(pixels, 0, width, 0, height/2, width, height/2);
        for (int color : pixels) {
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        int color = Color.rgb(1 - (R / n), 1 - (G / n) , 1 - (B / n));
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        company.setContrastColor(y >= 128 ? Color.BLACK : Color.WHITE);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        ProgressBar progInd = loadingIndicatorReference.get();
        if (progInd != null) {
            progInd.setVisibility(View.GONE);
        }
        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }

        CollapsingToolbarLayout toolbar = toolbarReference.get();
        if (toolbar != null) {
            toolbar.setExpandedTitleColor(company.getContrastColor());
        }
    }
}

