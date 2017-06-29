package com.example.marc.jobhelper.Controller;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.R;

public class running extends Fragment {

    private RecyclerView recyclerView;
    private CompanyListLoaderTask task;

    public running() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        reloadRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_running);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void reloadRecyclerView(){
        task = new CompanyListLoaderTask(recyclerView);
        String[] stati = {ApplicationStatus.SENT, ApplicationStatus.INT_PLANNED, ApplicationStatus.INT_HELD};
        task.execute(stati);
    }
}
