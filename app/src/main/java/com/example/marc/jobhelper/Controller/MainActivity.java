package com.example.marc.jobhelper.Controller;

import android.Manifest;
import android.content.Context;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.R;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_NUMBER = 1;
    private static Context context;
    //TODO Fragments für Geplant, laufend und erledigt anlegen
    //TODO Seitenwechsel zwischen Fragments ermöglichen
    //TODO Neuer Kontakt-Seite erstellen
    //TODO Bestehende Einträge in Liste anzeigen
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_planned:
                    fragment = new planned();
                    break;
                case R.id.navigation_running:
                    fragment = new running();
                    break;
                case R.id.navigation_done:
                    fragment = new completed();
                    break;
                default:
                    return false;
            }
            this.navigateToFragment(fragment);
            return true;
            //TODO Activity oder Fragment wechseln
        }

        private void navigateToFragment(Fragment fragment) {
            FragmentManager FragMan = getFragmentManager();
            FragmentTransaction trans = FragMan.beginTransaction();
            trans.replace(R.id.content, fragment);
            trans.commit();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getApplicationContext(), EditCompany.class);
                i.putExtra("ID", DatabaseConnection.DEFAULT_ID);
                startActivity(i);
            }
        });

        int permissionCheck = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //FIXME Zugriffsberechtigung fixen
        if (!(permissionCheck == PackageManager.PERMISSION_GRANTED)) {
            // User may have declined earlier, ask Android if we should show him a reason
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Company.setImagesAllowed(false);
                // show an explanation to the user
                // Good practise: don't block thread after the user sees the explanation, try again to request the permission.
            } else {
                // request the permission.
                // CALLBACK_NUMBER is a integer constants
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_NUMBER);
                // The callback method gets the result of the request.
            }
        } else {
            Company.setImagesAllowed(true);// got permission use it
        }

        //Beispieleinträge
        /**DatabaseConnection dbc = DatabaseConnection.getInstance(context);
        dbc.addCompany(new Company("Beispielfirma1", "Beispiel Job", ApplicationStatus.PLANNED, new Date(), "Addresse 1", "HansJürgen", Uri.parse("www.google.com"), "017621345", null));
        dbc.addCompany(new Company("Beispielfirma2", "Beispiel Job", ApplicationStatus.INT_PLANNED, new Date(), "Addresse 2", "HansJürgen", Uri.parse("www.google.com"), "017621345", null));
        dbc.addCompany(new Company("Beispielfirma3", "Beispiel Job", ApplicationStatus.DENIED, new Date(), "Addresse 3", "HansJürgen", Uri.parse("www.google.com"), "017621345", null));
**/
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_running);
        Fragment fragment = new running();
        FragmentManager FragMan = getFragmentManager();
        FragmentTransaction trans = FragMan.beginTransaction();
        trans.replace(R.id.content, fragment);
        trans.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_NUMBER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    Company.setImagesAllowed(true);
                } else {
                    // permission denied
                    Company.setImagesAllowed(false);
                    // Disable the functionality that depends on this permission.
                }
                return;
            }

            // other 'case' statements for other permssions
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
