

package com.example.marc.jobhelper.Controller;

        import android.graphics.Bitmap;
        import android.os.AsyncTask;
        import android.widget.ImageView;

        import com.example.marc.jobhelper.Model.Company;

        import java.lang.ref.WeakReference;

/**
 * Created by marc on 27.06.17.
 */

public class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private  Company company;

    public BitmapLoaderTask(ImageView imageView, Company company) {
        imageViewReference = new WeakReference<>(imageView);
        this.company = company;
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        if(company.getImgUri().toString().equals("")) return null;
        return company.loadThumbnail();
    }

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
