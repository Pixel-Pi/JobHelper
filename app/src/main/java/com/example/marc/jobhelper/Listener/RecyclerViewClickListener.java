package com.example.marc.jobhelper.Listener;

import android.content.Intent;
import android.view.View;

import com.example.marc.jobhelper.Controller.EditCompany;
import com.example.marc.jobhelper.Model.DatabaseConnection;

/**
 * Listener, der auf Tippen auf Einträge in den RecyclerViews in den Fragments
 * reagiert.
 * Created by marc on 24.06.17.
 */

public class RecyclerViewClickListener implements View.OnClickListener {

    /**
     * Index des Eintrages, auf den der Listener registriert wurde.
     */
    private int index;

    /**
     * Speichert den Index des Eintrages, auf den er Registriert ist.
     * @param index Index des Eintages
     */
    public RecyclerViewClickListener(int index){
        this.index = index;
    }

    /**
     * Öffnet die EditCompany Activity und fügt dem Intent den Index, bzw. die ID
     * des Eintrages bei.
     * @param v Nicht benötigt.
     */
    public void onClick(View v) {
        final Intent i = new Intent(v.getContext(), EditCompany.class);
        i.putExtra("ID", index);
        v.getContext().startActivity(i);
    }
}
