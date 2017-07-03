package com.example.marc.jobhelper.Listener;

import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marc on 25.06.17.
 */

public class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener {

    private Button button;

    public DatePickerDialogListener(Button button){
        this.button = button;
    }
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
