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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marc.jobhelper.Listener.DatePickerDialogListener;
import com.example.marc.jobhelper.Listener.TimePickerDialogListener;
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

    public static final String DESCRIBABLE_KEY = "Toolbar";

    private static Company company;
    private static CollapsingToolbarLayout editCompanyToolbar;
    private ImageView imageView;
    private EditText jobTitleInput;
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

        Spinner spinner = (Spinner) findViewById(R.id.statusSelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.appStati, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent i = getIntent();
        final int index = i.getIntExtra("ID", DatabaseConnection.DEFAULT_ID);
        System.out.println("Opened EditCompany with index " + index);

        //TODO make "EditCompany" editable and show the Companies name
        editCompanyToolbar = (CollapsingToolbarLayout) findViewById(R.id.editCompanyToolbar);
        jobTitleInput = (EditText) findViewById(R.id.jobTitleInput);
        dateInputButton = (Button) findViewById(R.id.dateButton); //TODO open Datepicker on click on EditTextfield
        timeInputButton = (Button) findViewById(R.id.timeButton);
        addressInput = (EditText) findViewById(R.id.addressInput);
        contactInput = (EditText) findViewById(R.id.contactInput);
        websiteInput = (EditText) findViewById(R.id.websiteInput);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        imageView = (ImageView) findViewById(R.id.companyLogo);


        editCompanyToolbar.setTitle("Neuer Eintrag");
        if(index != DatabaseConnection.DEFAULT_ID) {
            DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
            company = dbc.loadCompany(index);
            if(company == null) company = new Company();
            //TODO alle Textfelder mit infos beladen
            try {
                imageView.setImageBitmap(company.loadBitmap());
            } catch (Exception ex) {
                Toast.makeText(MainActivity.getAppContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            jobTitleInput.setText(company.getJobTitle());
            dateInputButton.setText(SimpleDateFormat.getDateInstance().format(company.getDate()));
            timeInputButton.setText(new SimpleDateFormat("HH:mm").format(company.getDate()));
            addressInput.setText(company.getAddress());
            contactInput.setText(company.getContactPerson());
            websiteInput.setText(company.getWebsite());
            phoneInput .setText(company.getPhone());


        }
        else company = new Company();

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
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                Date convertedDate = new Date();
                //TODO 1 Button for Date, 1 Button for Time
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
                //TODO save all textfields into the Company variable

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
                        System.out.println(imageReturnedIntent.getData());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_company, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editCompanyButton) {
            new EditCompanyTitlePopUp().show(getSupportFragmentManager(), getString(R.string.enterCompanyName));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(company == null) return;
        company.setStatus(ApplicationStatus.availableStati.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static CollapsingToolbarLayout getEditCompanyToolbar(){ return editCompanyToolbar; }
}
