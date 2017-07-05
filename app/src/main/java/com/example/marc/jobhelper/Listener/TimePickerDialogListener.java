package com.example.marc.jobhelper.Listener;

import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Listener für den DatePicker, der die Zeit vom Nutzer abfragt.
 * Created by marc on 26.06.17.
 */

public class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener{

    /**
     * Button, auf dem die Zeit angezeigt wird.
     */
    private Button button;

    /**
     * Speichert den Button lokal.
     * @param timeInputButton Der Button, auf dem die Zeit angezeigt wird.
     */
    public TimePickerDialogListener(Button timeInputButton) { this.button = timeInputButton;}


    /**
     * Reagiert auf das Event, wenn der Nutzer eine Zeit
     * @param timePicker View des TimePickerDialogs
     * @param hours Stunde, die gewählt wurde
     * @param minutes Minute, die gewählt wurde
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        button.setText(hours + ":" + String.format("%02d", minutes));
    }
}
