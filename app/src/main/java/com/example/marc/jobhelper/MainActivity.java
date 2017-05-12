package com.example.marc.jobhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
            switch (item.getItemId()) {
                case R.id.navigation_planned:
                    mTextMessage.setText(R.string.title_planned);
                    return true;
                case R.id.navigation_running:
                    mTextMessage.setText(R.string.title_running);
                    return true;
                case R.id.navigation_done:
                    mTextMessage.setText(R.string.title_done);
                    return true;
            }
            return false; //TODO Activity oder Fragment wechseln
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
