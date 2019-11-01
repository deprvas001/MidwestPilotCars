package com.midwestpilotcars.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.NewJobsAdapter;
import com.midwestpilotcars.models.getAllJobs.COMPLETE;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.getAllJobs.UPCOMING;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int temp;
    private RecyclerView jobsRecyclerView;
    private TextView empty_view;
    private View rootView;
    private ArrayList<UPCOMING> upcomingArrayList = new ArrayList<>();
    private ArrayList<ONGOING> ongoingArrayList = new ArrayList<>();
    private ArrayList<COMPLETE> completeArrayList = new ArrayList<>();

    private TextView textView;

    public PlaceHolderFragment() {
    }

    public static PlaceHolderFragment newInstance(int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_new_jobs, container, false);

                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_pending_jobs, container, false);

                break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_completed_jobs, container, false);

                break;
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.section_label);
        jobsRecyclerView = view.findViewById(R.id.jobs_recycler_view);
        empty_view = view.findViewById(R.id.empty_view);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        jobsRecyclerView.setHasFixedSize(true);

    }

    public void setNewJobData(ArrayList<UPCOMING> upcoming) {
        if(upcoming.size() == 0){
        jobsRecyclerView.setVisibility(View.GONE);
        empty_view.setVisibility(View.VISIBLE);
        }else{
            jobsRecyclerView.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }

        NewJobsAdapter newJobsAdapter = new NewJobsAdapter(upcoming,0);
        jobsRecyclerView.setAdapter(newJobsAdapter);
    }

    public void setOnGoingData(ArrayList<ONGOING> ongoing) {
        if(ongoing.size() == 0){
            jobsRecyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }else{
            jobsRecyclerView.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
        NewJobsAdapter newJobsAdapter = new NewJobsAdapter(ongoing,1);
        jobsRecyclerView.setAdapter(newJobsAdapter);
    }

    public void setCompleteData(ArrayList<COMPLETE> complete) {
        if(complete.size() == 0){
            jobsRecyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }else{
            jobsRecyclerView.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
        NewJobsAdapter newJobsAdapter = new NewJobsAdapter(complete,2);
        jobsRecyclerView.setAdapter(newJobsAdapter);
    }
}