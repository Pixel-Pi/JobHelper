package com.example.marc.jobhelper.Controller;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.marc.jobhelper.Model.Company;

import java.lang.ref.WeakReference;

/**
 * Task für das ansynchrone Laden von Bitmaps aus einer Company.
 * Wird haputsächlich für die Übersichten genutzt, weshalb die Thumbnails geladen werden.
 * Created by marc on 27.06.17.
 */

public class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {
    /**
     * Referenz auf die ImageView in der das Bild angezeigt werden soll.
     */
    private final WeakReference<ImageView> imageViewReference;

    /**
     * Die Firma, aus der das Bild geladen werden soll.
     */
    private  Company company;

    /**
     * Konstruktor. Legt die Referenzen zu der ImageView und der Company an.
     * @param imageView ImageView zum Anzeigen des Bildes.
     * @param company Firma, deren Bild geladen werden soll.
     */
    public BitmapLoaderTask(ImageView imageView, Company company) {
        imageViewReference = new WeakReference<>(imageView);
        this.company = company;
    }

    /**
     * Lädt das Thumbnail des Bildes.
     * @param args Keine Argumente benötigt.
     * @return Bitmap, die geladen wurde.
     */
    @Override
    protected Bitmap doInBackground(String... args) {
        if(company.getImgUri().toString().equals("")) return null;
        return company.loadThumbnail();
    }

    /**
     * Holt die ImageView aus der Referenz darauf und zeigt das geladene Bild darin an.
     * @param bitmap Das geladene Bild
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
