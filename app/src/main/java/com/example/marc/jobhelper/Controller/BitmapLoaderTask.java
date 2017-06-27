

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
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.company = company;
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... args) {
        // params comes from the execute() call: params[0] is the url.
        //TODO Check for Thumbnails and create, if no thumbnail is available
        return company.loadBitmap();
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
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
