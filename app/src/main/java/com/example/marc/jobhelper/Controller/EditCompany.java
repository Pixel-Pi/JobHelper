package com.example.marc.jobhelper.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditCompany extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int SELECT_PHOTO = 1;

    private static Company company;

    private static ImageView imageView;
    private static EditText dateInput;
    private static EditText addressInput;
    private static EditText contactInput;
    private static EditText websiteInput;
    private static EditText phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.statusSelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.appStati, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent i = getIntent();
        final int index = i.getIntExtra("ID", DatabaseConnection.DEFAULT_ID);

        //TODO make "EditCompany" editable and show the Companies name
        dateInput = (EditText) findViewById(R.id.dateInput); //TODO open Datepicker on click on EditTextfield
        addressInput = (EditText) findViewById(R.id.addressInput);
        contactInput = (EditText) findViewById(R.id.contactInput);
        websiteInput = (EditText) findViewById(R.id.websiteInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
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
            company = dbc.loadCompany(index);
            if(company == null) company = new Company();
            //TODO alle Textfelder mit infos beladen
            if(company.getImgUri() != null) {
                try {
                    imageView.setImageBitmap(company.loadBitmap());
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.getAppContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            dateInput.setText(company.getDate().toString());
            addressInput.setText(company.getAddress());
            contactInput.setText(company.getContactPerson());
            websiteInput.setText(company.getWebsite().toString());
            phoneInput .setText(company.getPhone());
        }
        else company = new Company();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
                Date convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(dateInput.getText().toString());
                    company.setDate(convertedDate);
                } catch (ParseException e) {
                    System.err.println(dateInput.getText().toString());
                    e.printStackTrace();
                }
                company.setAddress(addressInput.getText().toString());;
                company.setContactPerson(contactInput.getText().toString());
                company.setWebsite(Uri.parse(websiteInput.getText().toString()));
                company.setPhone(phoneInput.getText().toString());

                DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
                if(index != DatabaseConnection.DEFAULT_ID) dbc.removeCompanyAtIndex(index);
                dbc.addCompany(company);
                //TODO save all textfields into the Company variable
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
                        company.setImgUri(imageReturnedIntent.getData());
                        imageView.setImageBitmap(company.loadBitmap());
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(company == null) return;
        company.setStatus(ApplicationStatus.availableStati.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
