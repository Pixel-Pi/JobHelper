package com.example.marc.jobhelper.Controller;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.Model.MyAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Taks für das asynchrone Laden der Vorschaulisten.
 * Created by marc on 27.06.17.
 */

public class CompanyListLoaderTask extends AsyncTask<String, Void, MyAdapter> {
    /**
     * Referenz auf die RecyclerView in der die Liste angezeigt wird.
     */
    private final WeakReference<RecyclerView> recyclerViewReference;

    /**
     * Speichert die Referenz auf die RecyclerView.
     * @param recyclerView RecyclerView
     */
    public CompanyListLoaderTask(RecyclerView recyclerView) {
        recyclerViewReference = new WeakReference<>(recyclerView);
    }

    /**
     * Lädt alle Firmeneinträge aus der Datenbank und gleicht deren Status mit den in params gewünschten Stati ab.
     * Danach werden die Einträge noch nach dem Index sortiert.
     * @param params Die Stati, die gewünscht sind.
     * @return Liste aus den CompanyItems, die angezeigt werden sollen.
     */
    @Override
    protected MyAdapter doInBackground(String... params) {
        DatabaseConnection dbc = DatabaseConnection.getInstance(MainActivity.getAppContext());
        List<Company> companies = dbc.loadAllCompaines();
        List<MyAdapter.CompanyItem> myData = new ArrayList<>();
        MyAdapter.CompanyItem tempItem;
        if(companies != null) {
            for (Company c : companies) {
                for ( int i = 0; i<params.length; i++) {
                    if (c.getStatus().equals(params[i])) {
                        tempItem = new MyAdapter.CompanyItem(c);
                        myData.add(tempItem);
                    }
                }
            }
        }
        //Falls leer, wird ein Dummy-Element hinzugefügt.
        if(myData.isEmpty()) myData.add(new MyAdapter.CompanyItem());

        //Sortieren der Einträge nach Index.
        //Hier kann auch nach anderen Kriterien sortiert werden.
        Collections.sort(myData, new Comparator<MyAdapter.CompanyItem>() {
            @Override
            public int compare(MyAdapter.CompanyItem comp1, MyAdapter.CompanyItem comp2)
            {

                return  comp1.getIndex() - comp2.getIndex();
            }
        });
        return new MyAdapter(myData);
    }

    /**
     * Zeigt die Liste an CompanyItems in der RecyclerView an.
     * @param adapter Liste der CompanyItems
     */
    @Override
    protected void onPostExecute(MyAdapter adapter) {
        if (isCancelled()) {
            return;
        }

        if (recyclerViewReference != null) {
            RecyclerView recyclerView = recyclerViewReference.get();
            if (recyclerView != null) {
                recyclerView.setAdapter(adapter);
            }
        }
    }
}