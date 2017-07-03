package com.example.marc.jobhelper.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marc on 02.07.17.
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener{


    private Button dateInputButton;
    private Button timeInputButton;
    private Company company;

    public SpinnerListener(Company company, Button dateInputButton, Button timeInputButton){
        this.company = company;
        this.dateInputButton = dateInputButton;
        this.timeInputButton = timeInputButton;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(company == null) return;
        company.setStatus(ApplicationStatus.availableStati.get(position));

        if(company.getStatus().equals(ApplicationStatus.INT_HELD)){
            dateInputButton.setVisibility(View.GONE);
            timeInputButton.setVisibility(View.GONE);
            return;
        }
        else {
            dateInputButton.setVisibility(View.VISIBLE);
            timeInputButton.setVisibility(View.VISIBLE);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            String date = dateFormat.format(company.getDate());
            String time = timeFormat.format(company.getDate());

            if (!(dateFormat.format(new Date()).equals(date))) dateInputButton.setText(date);
            else dateInputButton.setText(R.string.dateButton);
            if (!(timeFormat.format(new Date()).equals(time))) timeInputButton.setText(time);
            else timeInputButton.setText(R.string.timeButton);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
