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

/**
 * Fragment, das alle erledigten Bewerbungen anzeigt. Hierzu zählen alle Company-Objekte, die den Status DENIED oder ACCEPTED haben.
 */
public class completed extends Fragment {

    /**
     * RecyclerView für die Liste an Firmen.
     */
    private RecyclerView recyclerView;

    /**
     * Task zum laden der Liste.
     */
    private CompanyListLoaderTask task;

    /**
     * Benötigter leerer Konstruktor
     */
    public completed() {
        // Required empty public constructor
    }

    /**
     * Lädt die Liste an Companies erneut bei Wiederanzeigen des Fragments.
     */
    @Override
    public void onResume() {
        super.onResume();
        reloadRecyclerView();
    }

    /**
     * Inflated die View und stellt die RecyclerView ein.
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstance
     * @return View, die angezeigt wird.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_completed);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    /**
     * Lädt die Liste an Companies, die angezeigt wird.
     * Companies müssen den Status ACCEPTED oder DENIED haben um angezeigt zu werden.
     */
    public void reloadRecyclerView(){
        task = new CompanyListLoaderTask(recyclerView);
        String[] stati = {ApplicationStatus.ACCEPTED, ApplicationStatus.DENIED};
        task.execute(stati);
    }
}
