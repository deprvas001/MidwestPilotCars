package com.midwestpilotcars.views.customViews;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.DefaultDataLoadAdapter;
import com.midwestpilotcars.callbacks.OnDefaultDataItemClicked;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.models.allDefaultModels.LOADTYPE;
import com.midwestpilotcars.models.allDefaultModels.TRUCKINGCOMPANY;

import java.util.ArrayList;

public class DefaultDataDialog extends DialogFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private ArrayList<Parcelable> allDefaultDataList;
    private OnDefaultDataItemClicked onDefaultDataItemClicked;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allDefaultDataList = getArguments().getParcelableArrayList(AppConstants.DEFAULT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Hello World");
        if(allDefaultDataList.get(0) instanceof LOADTYPE){
            getDialog().setTitle(getString(R.string.select_load_type));
        }else if(allDefaultDataList.get(0) instanceof TRUCKINGCOMPANY){
            getDialog().setTitle(getString(R.string.select_truck_company));

        }
        View view = inflater.inflate(R.layout.fragment_default_data_dialog, container, false);
        ListView listView = view.findViewById(R.id.listviewDefaultData);
        DefaultDataLoadAdapter defaultDataLoadAdapter = new DefaultDataLoadAdapter(view.getContext(), allDefaultDataList);
        listView.setAdapter(defaultDataLoadAdapter);
        onDefaultDataItemClicked = (OnDefaultDataItemClicked) getActivity();
        listView.setOnItemClickListener(this);
        listView.setOnItemSelectedListener(this);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("My Title");
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("HELLO","DUNIA");

        onDefaultDataItemClicked.onDefaultItemClicked(allDefaultDataList.get(position));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("HELLO","DUNIA1");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
