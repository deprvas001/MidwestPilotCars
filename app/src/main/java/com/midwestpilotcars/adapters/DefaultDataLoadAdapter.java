package com.midwestpilotcars.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.midwestpilotcars.R;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.allDefaultModels.LOADTYPE;
import com.midwestpilotcars.models.allDefaultModels.TRUCKINGCOMPANY;

import java.util.ArrayList;

public class DefaultDataLoadAdapter extends ArrayAdapter<Parcelable> {
    private ArrayList<Parcelable> loadtypes;

    public DefaultDataLoadAdapter(Context context, ArrayList<Parcelable> users) {
        super(context, R.layout.default_data_selector_layout, users);
        loadtypes = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Parcelable user = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.default_data_selector_layout, parent, false);
            viewHolder.tvName = convertView.findViewById(R.id.radionButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (user instanceof LOADTYPE) {
            LOADTYPE loadtype = (LOADTYPE) user;
            viewHolder.tvName.setText(loadtype.getLoadTypeName());
        } else if (user instanceof TRUCKINGCOMPANY) {
            TRUCKINGCOMPANY truckingcompany = (TRUCKINGCOMPANY) user;
            viewHolder.tvName.setText(truckingcompany.getTruckingCompanyName());
        }
        return convertView;

    }

    @Override
    public Parcelable getItem(int position) {
        return loadtypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tvName;
    }

    @Override
    public int getCount() {
        return loadtypes.size();
    }
}