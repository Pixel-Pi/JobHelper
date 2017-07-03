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
 * Created by marc on 27.06.17.
 */

public class CompanyListLoaderTask extends AsyncTask<String, Void, MyAdapter> {
    private final WeakReference<RecyclerView> recyclerViewReference;
    private  Company company;

    public CompanyListLoaderTask(RecyclerView recyclerView) {
        recyclerViewReference = new WeakReference<>(recyclerView);
    }

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
        if(myData.isEmpty()) myData.add(new MyAdapter.CompanyItem());
        Collections.sort(myData, new Comparator<MyAdapter.CompanyItem>() {
            @Override
            public int compare(MyAdapter.CompanyItem comp1, MyAdapter.CompanyItem comp2)
            {

                return  comp1.getIndex() - comp2.getIndex();
            }
        });
        return new MyAdapter(myData);
    }

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