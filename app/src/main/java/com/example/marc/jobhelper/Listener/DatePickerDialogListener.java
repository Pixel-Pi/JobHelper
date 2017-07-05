package com.example.marc.jobhelper.Listener;

import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listener für den DatePicker in der EditCompany Activity.
 * Created by marc on 25.06.17.
 */

public class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener {

    /**
     * Button, auf dem das Datum angezeigt wird.
     */
    private Button button;

    /**
     * Speichert den Button lokal.
     * @param button Der Button, auf dem das Datum angezeigt wird.
     */
    public DatePickerDialogListener(Button button){
        this.button = button;
    }

    /**
     * Reagiert auf das Event, wenn der Nutzer ein Datum ausgewählt hat.
     * @param view View des DatePickerDialogs
     * @param year Jahr, das gewählt wurde.
     * @param month Monat, der gewählt wurde.
     * @param dayOfMonth Tag, der gewählt wurde.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(dayOfMonth + "/" + month + "/" + year);
        } catch (ParseException e) {

        }

        button.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
    }
}
