package com.example.marc.jobhelper.Model;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.jobhelper.Controller.EditCompany;
import com.example.marc.jobhelper.Controller.MainActivity;
import com.example.marc.jobhelper.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private List<CompanyItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtCompName;
        public TextView txtJobTitle;
        public TextView txtAppStatus;
        public ImageView img;

        public ViewHolder(View v) {
            super(v);
            txtCompName = (TextView) v.findViewById(R.id.company_name);
            txtJobTitle = (TextView) v.findViewById(R.id.job_title);
            txtAppStatus = (TextView) v.findViewById(R.id.application_status);
            img = (ImageView) v.findViewById(R.id.image_company);
        }
    }

    public void add(int position, CompanyItem item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(CompanyItem item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public static class CompanyItem {
        private Bitmap img;
        private String CompanyName;
        private String JobTitle;
        private String Status;

        public CompanyItem(Bitmap _img, String _CompName, String _JobTitle, String _Status){
            CompanyName = _CompName;
            JobTitle = _JobTitle;
            Status = _Status;
            img = _img;
            /**
            if(imgLink == null || imgLink.equals(""))return;
            try{
                img =
            }
            catch(Exception ex){
                Toast.makeText(MainActivity.getAppContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
             **/
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<CompanyItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final CompanyItem item = mDataset.get(position);
        holder.txtCompName.setText(mDataset.get(position).CompanyName);
        /*holder.txtJobTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });*/
        //TODO OnClickListener bei Konstruktor erstellen
        //TODO Alle Listener in eigene Klassen
        holder.txtJobTitle.setText(mDataset.get(position).JobTitle);
        holder.txtAppStatus.setText(mDataset.get(position).Status);
        if(mDataset.get(position).img != null) holder.img.setImageBitmap(mDataset.get(position).img);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //FIXME Get clicked Object and start EditCompany Intent
    public void onClick(View v) {
        final Intent i = new Intent(v.getContext(), EditCompany.class);
        i.putExtra("ID", DatabaseConnection.DEFAULT_ID);
        v.getContext().startActivity(i);
    }

}