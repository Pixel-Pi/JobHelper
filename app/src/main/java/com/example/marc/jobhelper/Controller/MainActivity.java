package com.example.marc.jobhelper.Controller;

import android.Manifest;
import android.content.Context;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.marc.jobhelper.Listener.NavBarListener;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.R;

/**
 * Die MainActivity, die beim Starten der App angezeigt wird.
 * Sie beinhaltet eine BottomnavigationView zum Wechseln der Fragments sowie einen Floating Action Button
 * zum Erstellen eines neuen Eintrages.
 * Außerdem holt sie sich die Runtime-Permission zum Lesen und Schreiben auf der SD-Karte, um später die
 * Bilder und Thumbnails laden und erstellen zu können.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Callback Nummer für die Anfrage auf Schreib- (und Lese-) berechtigung.
     */
    private static final int WRITE_EXTERNAL_NUMBER = 1;
    private static Context context;
    private NavBarListener navBarListener = new NavBarListener(this);

    /**
     * Erfragt die Lese- und Schreibberechtigung für die SD-Karte und zeigt das mittlere Fragment an.
     * @param savedInstanceState
     */
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

        //Berechtigung überprüfen und gegebenenfalls anfordern.
        int permissionCheck = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!(permissionCheck == PackageManager.PERMISSION_GRANTED)) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Company.setImagesAllowed(false);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_NUMBER);
            }
        }
        else {
            Company.setImagesAllowed(true);
        }

        //Standartmäßig das running-Fragment anzeigen.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navBarListener);
        navigation.setSelectedItemId(R.id.navigation_running);
        Fragment fragment = new running();
        FragmentManager FragMan = getFragmentManager();
        FragmentTransaction trans = FragMan.beginTransaction();
        trans.replace(R.id.content, fragment);
        trans.commit();
    }

    /**
     * Callbackmethode für die Anfragen für Berechtigungen. Hier wird nur auf die Schreibberechtigung reagiert.
     * @param requestCode Nummer um zu ermitteln welche Berechtigung gewährt wurde.
     * @param permissions Rechte, die angefragt wurden.
     * @param grantResults Rechte, die erteilt wurden.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_NUMBER: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Company.setImagesAllowed(true);
                } else {
                    Company.setImagesAllowed(false);
                }
                return;
            }
        }
    }

    /**
     * Kleine Methode, um den Kontext zu holen.
     * @return Kontext der App
     */
    public static Context getAppContext() {
        return MainActivity.context;
    }

    /**
     * Navigiert zu einem Fragment.
     * @param fragment Fragment, das angezeigt werden soll.
     */
    public void navigateToFragment(Fragment fragment) {
        FragmentManager FragMan = getFragmentManager();
        FragmentTransaction trans = FragMan.beginTransaction();
        trans.replace(R.id.content, fragment);
        trans.commit();
    }

}
