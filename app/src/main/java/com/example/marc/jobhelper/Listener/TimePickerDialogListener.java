package com.example.marc.jobhelper.Listener;

import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by marc on 26.06.17.
 */

public class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener{

    private Button button;

    public TimePickerDialogListener(Button timeInputButton) { this.button = timeInputButton;}


    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        button.setText(hours + ":" + String.format("%02d", minutes));
    }
}
