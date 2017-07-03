package com.example.marc.jobhelper.Controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marc.jobhelper.Listener.DatePickerDialogListener;
import com.example.marc.jobhelper.Listener.SpinnerListener;
import com.example.marc.jobhelper.Listener.TimePickerDialogListener;
import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditCompany extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;

    public static final String DESCRIBABLE_KEY = "Toolbar";

    private Company company;
    private CollapsingToolbarLayout editCompanyToolbar;
    private ImageView imageView;
    private ProgressBar progressIndicator;
    private EditText jobTitleInput;
    private Spinner spinner;
    private Button dateInputButton;
    private Button timeInputButton;
    private EditText addressInput;
    private EditText contactInput;
    private EditText websiteInput;
    private EditText phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        final int index = i.getIntExtra("ID", DatabaseConnection.DEFAULT_ID);

        progressIndicator = (ProgressBar) findViewById(R.id.imageLoadingProgress);
        editCompanyToolbar = (CollapsingToolbarLayout) findViewById(R.id.editCompanyToolbar);
        jobTitleInput = (EditText) findViewById(R.id.jobTitleInput);

        dateInputButton = (Button) findViewById(R.id.dateButton);
        timeInputButton = (Button) findViewById(R.id.timeButton);
        addressInput = (EditText) findViewById(R.id.addressInput);
        contactInput = (EditText) findViewById(R.id.contactInput);
        websiteInput = (EditText) findViewById(R.id.websiteInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        imageView = (ImageView) findViewById(R.id.companyLogo);

        spinner = (Spinner) findViewById(R.id.statusSelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.appStati, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        editCompanyToolbar.setTitle("Neuer Eintrag");
        if(index != DatabaseConnection.DEFAULT_ID) {
            DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
            company = dbc.loadCompany(index);
            if(company == null) company = new Company();
            try {
                imageView.setImageBitmap(company.loadBitmap());
                editCompanyToolbar.setExpandedTitleColor(company.getContrastColor());
            } catch (Exception ex) {
                Toast.makeText(MainActivity.getAppContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            editCompanyToolbar.setTitle(company.getCompanyName());
            jobTitleInput.setText(company.getJobTitle());

            int j = 0;
            switch (company.getStatus()){
                case ApplicationStatus.PLANNED: j=0;
                    break;
                case ApplicationStatus.SENT: j=1;
                    break;
                case ApplicationStatus.INT_PLANNED: j=2;
                    break;
                case ApplicationStatus.INT_HELD: j=3;
                    break;
                case ApplicationStatus.DENIED: j=4;
                    break;
                case ApplicationStatus.ACCEPTED: j=5;
                    break;
            }
            spinner.setSelection(j);


            dateInputButton.setText(SimpleDateFormat.getDateInstance().format(company.getDate()));
            timeInputButton.setText(new SimpleDateFormat("HH:mm").format(company.getDate()));
            addressInput.setText(company.getAddress());
            contactInput.setText(company.getContactPerson());
            websiteInput.setText(company.getWebsite());
            phoneInput .setText(company.getPhone());


        }
        else company = new Company();

        spinner.setOnItemSelectedListener(new SpinnerListener(company, dateInputButton, timeInputButton));

        dateInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(v.getContext(), new DatePickerDialogListener(dateInputButton), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        timeInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                TimePickerDialog tpd = new TimePickerDialog(v.getContext(), new TimePickerDialogListener(timeInputButton), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),true);
                tpd.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                company.setCompanyName(editCompanyToolbar.getTitle().toString());
                company.setJobTitle(jobTitleInput.getText().toString());
                company.setStatus(ApplicationStatus.availableStati.get(spinner.getSelectedItemPosition()));
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                Date convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(dateInputButton.getText().toString() + " " + timeInputButton.getText().toString());
                    company.setDate(convertedDate);
                } catch (ParseException e) {
                    System.err.println(dateInputButton.getText().toString());
                    e.printStackTrace();
                }
                company.setAddress(addressInput.getText().toString());;
                company.setContactPerson(contactInput.getText().toString());
                company.setWebsite(Uri.parse(websiteInput.getText().toString()));
                company.setPhone(phoneInput.getText().toString());

                DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
                if(index != DatabaseConnection.DEFAULT_ID) dbc.removeCompanyAtIndex(index);
                dbc.addCompany(company);

                finish();
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
                        EditCompanyImageLoaderTask task = new EditCompanyImageLoaderTask(imageView, editCompanyToolbar, progressIndicator, company);
                        task.execute();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_company, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.editCompanyButton) {
            EditCompanyTitlePopUp popUp = new EditCompanyTitlePopUp();
            popUp.show(getSupportFragmentManager(), getString(R.string.enterCompanyName));
            return true;
        }
        else if(id == R.id.editLogoButton) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            return true;
        }
        else if(id == R.id.deleteButton){
            DatabaseConnection.getInstance(getApplicationContext()).removeCompanyAtIndex(company.getIndex());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCompanyTitle(String title){
        editCompanyToolbar.setTitle(title);
    }
}
