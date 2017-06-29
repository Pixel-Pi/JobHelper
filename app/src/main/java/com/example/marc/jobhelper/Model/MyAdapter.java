package com.example.marc.jobhelper.Model;
import java.text.SimpleDateFormat;
import java.util.List;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marc.jobhelper.Controller.BitmapLoaderTask;
import com.example.marc.jobhelper.Listener.RecyclerViewClickListener;
import com.example.marc.jobhelper.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
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
        public int index;

        public ViewHolder(View v) {
            super(v);
            txtCompName = (TextView) v.findViewById(R.id.company_name);
            txtJobTitle = (TextView) v.findViewById(R.id.job_title);
            txtAppStatus = (TextView) v.findViewById(R.id.application_status);
            img = (ImageView) v.findViewById(R.id.image_company);
        }
    }

    public static class CompanyItem {

        private int index;
        private Company company;

        private String CompanyName;
        private String JobTitle;
        private String Status;
        private Uri imgUri;

        public CompanyItem(){
            index = DatabaseConnection.DEFAULT_ID;
            company = null;

            CompanyName = "Keine Einträge";
            JobTitle = "Hier tippen für neuen Eintrag";
            Status = "";
            imgUri = Uri.parse("");

        }
        public CompanyItem(Company company){
            this.company = company;
            CompanyName = company.getCompanyName();
            JobTitle = company.getJobTitle();
            Status = company.getStatus();
            imgUri = company.getImgUri();
            index = company.getIndex();
        }

        public int getIndex(){return index;}


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
        holder.itemView.setOnClickListener(new RecyclerViewClickListener(mDataset.get(position).getIndex()));
        holder.txtJobTitle.setText(mDataset.get(position).JobTitle);

        String statusString = "Fehler";
        switch (mDataset.get(position).Status) {
            case ApplicationStatus.PLANNED: statusString = "Geplant";
                break;
            case ApplicationStatus.SENT: statusString = "Bewerbung abgesendet";
                break;
            case ApplicationStatus.INT_PLANNED: statusString = "Interview geplant am " + SimpleDateFormat.getDateInstance().format(mDataset.get(position).company.getDate());
                break;
            case ApplicationStatus.INT_HELD: statusString = "Interview gehalten";
                break;
            case ApplicationStatus.DENIED: statusString = "Abgelehnt";
                break;
            case ApplicationStatus.ACCEPTED: statusString = "Akzeptiert";
                break;
        }
        holder.txtAppStatus.setText(statusString);
        if(!mDataset.get(position).imgUri.toString().equals("")){
            BitmapLoaderTask task = new BitmapLoaderTask(holder.img, mDataset.get(position).company);
            task.execute();
        };
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}