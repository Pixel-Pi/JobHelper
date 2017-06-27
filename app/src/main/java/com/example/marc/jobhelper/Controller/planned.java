package com.example.marc.jobhelper.Controller;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.marc.jobhelper.Model.ApplicationStatus;
import com.example.marc.jobhelper.Model.Company;
import com.example.marc.jobhelper.Model.DatabaseConnection;
import com.example.marc.jobhelper.Model.MyAdapter;
import com.example.marc.jobhelper.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class planned extends Fragment implements AdapterView.OnItemClickListener{

    RecyclerView recyclerView;
    CompanyListLoaderTask task;

    public planned() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        reloadRecyclerView();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        reloadRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planned, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_planned);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        reloadRecyclerView();
        return view;
    }

    public void reloadRecyclerView(){
        task = new CompanyListLoaderTask(recyclerView);
        task.execute(ApplicationStatus.PLANNED);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
