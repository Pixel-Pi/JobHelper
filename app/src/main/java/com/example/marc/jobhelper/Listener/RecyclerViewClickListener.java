package com.example.marc.jobhelper.Listener;

import android.content.Intent;
import android.view.View;

import com.example.marc.jobhelper.Controller.EditCompany;
import com.example.marc.jobhelper.Model.DatabaseConnection;

/**
 * Created by marc on 24.06.17.
 */

public class RecyclerViewClickListener implements View.OnClickListener {

    private int index;
    public RecyclerViewClickListener(int index){
        System.out.println("New RecyclerViewClickListener with index " + index);
        this.index = index;
    }
    //FIXME Get clicked Object and start EditCompany Intent
    public void onClick(View v) {
        final Intent i = new Intent(v.getContext(), EditCompany.class);
        i.putExtra("ID", index);
        v.getContext().startActivity(i);
    }
}
