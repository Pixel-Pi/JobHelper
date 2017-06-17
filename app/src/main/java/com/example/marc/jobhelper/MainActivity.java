package com.example.marc.jobhelper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
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
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getApplicationContext(), EditCompany.class);
                startActivity(i);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_running);
        Fragment fragment = new running();
        FragmentManager FragMan = getFragmentManager();
        FragmentTransaction trans = FragMan.beginTransaction();
        trans.replace(R.id.content, fragment);
        trans.commit();
    }

}
