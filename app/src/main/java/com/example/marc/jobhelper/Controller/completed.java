package com.example.marc.jobhelper.Controller;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.Model.MyAdapter;
import com.example.marc.jobhelper.R;

import java.util.ArrayList;
import java.util.List;


public class completed extends Fragment {

    RecyclerView recyclerView;
    CompanyListLoaderTask task;

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

        reloadRecyclerView();
        return view;
    }

    public void reloadRecyclerView(){
        task = new CompanyListLoaderTask(recyclerView);
        String[] stati = {ApplicationStatus.ACCEPTED, ApplicationStatus.DENIED};
        task.execute(stati);
    }
}
