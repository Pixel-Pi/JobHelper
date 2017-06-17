package com.example.marc.jobhelper.Controller;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marc.jobhelper.Model.MyAdapter;
import com.example.marc.jobhelper.R;

import java.util.ArrayList;
import java.util.List;


public class running extends Fragment {

    RecyclerView recyclerView;

    public running() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_running);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        List<MyAdapter.CompanyItem> myData = new ArrayList<>();
        MyAdapter.CompanyItem tempItem;
        for(int i = 0; i < 10; i++){
            tempItem = new MyAdapter.CompanyItem(null, "Company "+ i, "JobTitle "+ i, "Läuft");
            myData.add(tempItem);
        }

        MyAdapter adapter = new MyAdapter(myData);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
