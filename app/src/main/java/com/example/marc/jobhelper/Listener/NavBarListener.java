package com.example.marc.jobhelper.Listener;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.marc.jobhelper.Controller.MainActivity;
import com.example.marc.jobhelper.Controller.completed;
import com.example.marc.jobhelper.Controller.planned;
import com.example.marc.jobhelper.Controller.running;
import com.example.marc.jobhelper.R;

/**
 * Created by marc on 02.07.17.
 */

public class NavBarListener implements BottomNavigationView.OnNavigationItemSelectedListener{

    private MainActivity mainActivity;

    public NavBarListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

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
        mainActivity.navigateToFragment(fragment);
        return true;
    }
}
