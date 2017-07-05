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
 * Fragment, dass alle geplanten Bewerbungen anzeigen soll. Hierzu zählen alle Company-Objekte, die den Status PLANNED haben.
 */
public class planned extends Fragment {

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
    public planned() {
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
        View view = inflater.inflate(R.layout.fragment_planned, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_planned);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    /**
     * Lädt die Liste an Companies, die angezeigt wird.
     * Companies müssen den Status PLANNED haben um angezeigt zu werden.
     */
    public void reloadRecyclerView(){
        task = new CompanyListLoaderTask(recyclerView);
        task.execute(ApplicationStatus.PLANNED);
    }
}
