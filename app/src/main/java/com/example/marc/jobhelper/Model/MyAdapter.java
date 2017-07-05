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

/**
 * Adapter, der für das Anzeigen der Listenelemente im RecyclerView in den planned, running und completed Fragmenten zustöndig ist.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<CompanyItem> mDataset;

    /**
     * ViewHolder für je ein Listenelement.
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCompName;
        private TextView txtJobTitle;
        private TextView txtAppStatus;
        private ImageView img;

        /**
         * Speichert sich die einzelnen Anzeigelemente.
         * @param v
         */
        private ViewHolder(View v) {
            super(v);
            txtCompName = (TextView) v.findViewById(R.id.company_name);
            txtJobTitle = (TextView) v.findViewById(R.id.job_title);
            txtAppStatus = (TextView) v.findViewById(R.id.application_status);
            img = (ImageView) v.findViewById(R.id.image_company);
        }
    }

    /**
     * Repräsentiert die Daten eines Listenelements.
     */
    public static class CompanyItem {

        private int index;
        private Company company;

        private String CompanyName;
        private String JobTitle;
        private String Status;
        private Uri imgUri;

        /**
         * Standardkonstruktor, erstellt Informationsitem
         */
        public CompanyItem(){
            index = DatabaseConnection.DEFAULT_ID;
            company = null;

            CompanyName = "Keine Einträge";
            JobTitle = "Hier tippen für neuen Eintrag";
            Status = " ";
            imgUri = Uri.parse("");

        }

        /**
         * Holt sich die Infos aus dem angegebenen Comapny Element und speichert sie.
         * @param company
         */
        public CompanyItem(Company company){
            this.company = company;
            CompanyName = company.getCompanyName();
            JobTitle = company.getJobTitle();
            Status = company.getStatus();
            imgUri = company.getImgUri();
            index = company.getIndex();
        }

        /**
         * Holt den Index dieses CompanyItems, der mit dem einer Company übereinstimmt.
         * @return Index CompanyItem = Index Company
         */
        public int getIndex(){return index;}


    }

    /**
     * Speichert die Liste aus CompanyItems
     * @param myDataset List
     */
    public MyAdapter(List<CompanyItem> myDataset) {
        mDataset = myDataset;
    }

    /**
     * Erstellt den ViewHolder
     * @param parent Vater des ViewHolders
     * @param viewType Nicht benötigt
     * @return ViewHolder
     */
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Lädt die Items aus dem mDataset und setzt die Parameter ein.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CompanyItem item = mDataset.get(position);
        holder.txtCompName.setText(item.CompanyName);
        holder.itemView.setOnClickListener(new RecyclerViewClickListener(item.getIndex()));
        holder.txtJobTitle.setText(item.JobTitle);

        String statusString = "Fehler";
        switch (item.Status) {
            case ApplicationStatus.PLANNED: statusString = "Geplant";
                break;
            case ApplicationStatus.SENT: statusString = "Bewerbung abgesendet";
                break;
            case ApplicationStatus.INT_PLANNED: statusString = "Interview geplant am " + SimpleDateFormat.getDateInstance().format(item.company.getDate());
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
            BitmapLoaderTask task = new BitmapLoaderTask(holder.img, item.company);
            task.execute();
        };
    }

    /**
     * Gibt die Anzahl der Elemente in mDataset, und somit auch die Listenelemente zurück.
     * @return Anzahl der Listenelemente.
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}