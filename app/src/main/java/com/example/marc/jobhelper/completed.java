package com.example.marc.jobhelper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class completed extends Fragment {

    RecyclerView recyclerView;

    public completed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_completed);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        List<MyAdapter.CompanyItem> myData = new ArrayList<>();
        MyAdapter.CompanyItem tempItem;
        for(int i = 0; i < 10; i++){
            tempItem = new MyAdapter.CompanyItem(null, "Company "+ i, "JobTitle "+ i, "Beendet");
            myData.add(tempItem);
        }

        MyAdapter adapter = new MyAdapter(myData);
        recyclerView.setAdapter(adapter);
        return view;
    }


}
