package com.example.marc.jobhelper.Listener;

import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.marc.jobhelper.Controller.MainActivity;
import com.example.marc.jobhelper.Controller.completed;
import com.example.marc.jobhelper.Controller.planned;
import com.example.marc.jobhelper.Controller.running;
import com.example.marc.jobhelper.R;

/**
 * Listener, der auf Tippen auf die Navbar reagiert, und zu anderem Fragment wechselt.
 * Created by marc on 02.07.17.
 */

public class NavBarListener implements BottomNavigationView.OnNavigationItemSelectedListener{

    /**
     * Die MainActivity, in der das Fragment gewechselt wird.
     */
    private MainActivity mainActivity;

    /**
     * Speichert die MainActivity.
     * @param mainActivity Die MainActivity, in der das Fragment gewechselt wird.
     */
    public NavBarListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    /**
     * Ermittelt, auf welches Item in der Navigation Bar getippt wurde und leitet die Navigation
     * zu diesem Fragment ein.
     * @param item Item, auf das getippt wurde.
     * @return true, falls der Listener korrekt reagieren konnte.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
        mainActivity.navigateToFragment(fragment);
        return true;
    }
}
