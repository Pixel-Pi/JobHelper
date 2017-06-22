package com.example.marc.jobhelper.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.R;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class EditCompany extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;
    private static ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        int index = i.getIntExtra("ID", DatabaseConnection.DEFAULT_ID);

        imageView = (ImageView) findViewById(R.id.companyLogo);
        ImageButton pickImage = (ImageButton) findViewById(R.id.pick_img);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        if(index != DatabaseConnection.DEFAULT_ID) {
            DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
            Company company = dbc.loadCompany(index);
            if(company.getImgUri() != null) {
                try {
                    imageView.setImageBitmap(loadBitmap(company.getImgUri()));
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.getAppContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        imageView.setImageBitmap(loadBitmap(imageUri));
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
        }
    }

    protected Bitmap loadBitmap(Uri imageUri){
        try {
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
