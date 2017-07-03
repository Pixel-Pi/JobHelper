package com.example.marc.jobhelper.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.marc.jobhelper.R;

/**
 * Created by marc on 25.06.17.
 */

public class EditCompanyTitlePopUp extends DialogFragment {

    private CollapsingToolbarLayout toolbar;

    public EditCompanyTitlePopUp(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_company_name_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText textfeld = (EditText)view.findViewById(R.id.editCompanyTextField);
                        ((EditCompany)getActivity()).setCompanyTitle(textfeld.getText().toString());
                    }
                })
                .setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}