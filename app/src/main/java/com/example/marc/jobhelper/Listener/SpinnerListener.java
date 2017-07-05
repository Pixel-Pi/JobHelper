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
 * Listener der auf die Auswahl eines Items im Spinner für die Stati in der EditCompany-Activity reagiert.
 * Created by marc on 02.07.17.
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener{

    /**
     * Button für Datumseingabe.
     */
    private Button dateInputButton;

    /**
     * Button für Zeiteingabe.
     */
    private Button timeInputButton;

    /**
     * Company, die gerade geöffnet ist und deren Status geändert wird.
     */
    private Company company;

    /**
     * Speichert sich die Referenzen auf die Objekte.
     * @param company
     * @param dateInputButton
     * @param timeInputButton
     */
    public SpinnerListener(Company company, Button dateInputButton, Button timeInputButton){
        this.company = company;
        this.dateInputButton = dateInputButton;
        this.timeInputButton = timeInputButton;
    }

    /**
     * Wird aufgerufen, wenn ein Item im Spinner ausgewählt wurde.
     * Anschließend wird der Status der Firma eingestellt und das Entsprechende Datum geladen und auf den Buttons angezeigt.
     * Wenn noch kein Datum eingegeben wurde, wird ein Platzhaltertext auf den Buttons angezeigt.
     * Falls der Status "INT_HELD" ist, werden die Buttons ausgeblendet.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(company == null) return;
        company.setStatus(ApplicationStatus.availableStati.get(position));

        //Falls Interview gehalten wurde, Datums und Zeitbuttons ausblenden, weil die an der Stelle keine Information haben.f
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

    /**
     * Wird aufgerufen, wenn kein Item ausgewählt wurde.
     * Es passiert nichts.
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
