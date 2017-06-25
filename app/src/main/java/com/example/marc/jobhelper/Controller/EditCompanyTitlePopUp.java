package com.example.marc.jobhelper.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import com.example.marc.jobhelper.R;

/**
 * Created by marc on 25.06.17.
 */

public class EditCompanyTitlePopUp extends DialogFragment {

    private CollapsingToolbarLayout toolbar;

    public EditCompanyTitlePopUp(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        builder.setMessage(R.string.enterCompanyName)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //EditCompany.getEditCompanyToolbar().setTitle();
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}