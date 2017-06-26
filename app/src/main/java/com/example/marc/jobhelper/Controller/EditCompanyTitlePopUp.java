package com.example.marc.jobhelper.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.marc.jobhelper.R;

import java.util.zip.Inflater;

/**
 * Created by marc on 25.06.17.
 */

public class EditCompanyTitlePopUp extends DialogFragment {

    private CollapsingToolbarLayout toolbar;

    public EditCompanyTitlePopUp(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_company_name_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        builder.setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText textfeld = (EditText)view.findViewById(R.id.editCompanyTextField);
                        EditCompany.getEditCompanyToolbar().setTitle(textfeld.getText().toString());
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