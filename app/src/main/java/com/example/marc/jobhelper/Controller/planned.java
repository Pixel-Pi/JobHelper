package com.example.marc.jobhelper.Controller;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.marc.jobhelper.Model.MyAdapter;
import com.example.marc.jobhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class planned extends Fragment implements AdapterView.OnItemClickListener{

   RecyclerView recyclerView;

    public planned() {
        // Required empty public constructor
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

        List<MyAdapter.CompanyItem> myData = new ArrayList<>();
        MyAdapter.CompanyItem tempItem;
        for(int i = 0; i < 10; i++){
            tempItem = new MyAdapter.CompanyItem(null, "Company "+ i, "JobTitle "+ i, "Geplant");
            myData.add(tempItem);
        }

        MyAdapter adapter = new MyAdapter(myData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
