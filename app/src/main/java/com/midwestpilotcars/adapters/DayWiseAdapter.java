package com.midwestpilotcars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;

import java.util.List;

public class DayWiseAdapter extends RecyclerView.Adapter<DayWiseAdapter.MyViewHolder> {
    private List<DayWiseTravell> day_List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day_travell;
        public ImageView end_destination;

        public MyViewHolder(View view) {
            super(view);
            day_travell = (TextView) view.findViewById(R.id.day_travell);
            end_destination = (ImageView)view.findViewById(R.id.end_destination);
        }
    }

    public DayWiseAdapter(List<DayWiseTravell> day_List) {
        this.day_List = day_List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_wise_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DayWiseTravell day = day_List.get(position);
        holder.day_travell.setText(day.getDay_travell());

        if(position == day_List.size()-1){
            holder.end_destination.setVisibility(View.VISIBLE);
        }else{
            holder.end_destination.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return day_List.size();
    }
}
