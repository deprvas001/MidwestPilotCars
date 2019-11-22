package com.midwestpilotcars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;
import com.midwestpilotcars.models.wallet.AllDayAmount;
import com.midwestpilotcars.models.wallet.DayExpense;

import java.util.List;

public class AllDayExpenseAdapter extends RecyclerView.Adapter<AllDayExpenseAdapter.MyViewHolder> {
    private List<DayExpense> day_List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day_count,costing_from,day_wise_rate,no_go_price,mile_travel_calulate;

        public MyViewHolder(View view) {
            super(view);
            day_count = (TextView) view.findViewById(R.id.day_count);
            costing_from = (TextView)view.findViewById(R.id.choose_costing_from);
            day_wise_rate = (TextView)view.findViewById(R.id.day_wise_date_rate);
            no_go_price = (TextView)view.findViewById(R.id.no_go_price);
            mile_travel_calulate = (TextView)view.findViewById(R.id.miles_travell_caluclate);
        }
    }

    public AllDayExpenseAdapter(List<DayExpense> day_List) {
        this.day_List = day_List;
    }

    @Override
    public AllDayExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_day_expense, parent, false);
        return new AllDayExpenseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllDayExpenseAdapter.MyViewHolder holder, int position) {
        DayExpense day = day_List.get(position);
        holder.day_count.setText("Day "+String.valueOf(position+1));
        holder.mile_travel_calulate.setVisibility(View.GONE);
        holder.no_go_price.setVisibility(View.GONE);
        if(day.getNo_go_day().equals("1")){
            if(day.getChoose_costing_from().equals("1")){
                holder.day_wise_rate.setVisibility(View.GONE);
                holder.mile_travel_calulate.setVisibility(View.VISIBLE);
                holder.costing_from.setText("Choose Costing From : Per Mile Cost" );
                holder.mile_travel_calulate.setText(
                        "No. Of Mile Travelled = "+ day.getNo_of_miles_travelled()+ "\n"+
                        "Day Wise Day Rate : "+
                        "No. Of Mile Travelled"+ " * Per Mile Cost"
                        +" = $ "+day.getDay_wise_day_rate() );
            }else if(day.getChoose_costing_from().equals("2")){
                holder.costing_from.setText("Choose Costing From : Per Day Cost" );
                holder.day_wise_rate.setVisibility(View.VISIBLE);
                holder.day_wise_rate.setText("Day Wise Day Rate : $"+day.getDay_wise_day_rate());
            }else{
                holder.day_wise_rate.setVisibility(View.VISIBLE);
                holder.costing_from.setText("Choose Costing From : Mini Cost" );
                holder.day_wise_rate.setText("Day Wise Day Rate : $"+day.getDay_wise_day_rate());
            }
        }else{
            holder.no_go_price.setText("No Go Day : Yes" );
            holder.no_go_price.setVisibility(View.VISIBLE);
            holder.day_wise_rate.setText("Day Wise Day Rate : $"+day.getDay_wise_day_rate());
        }

    }

    @Override
    public int getItemCount() {
        return day_List.size();
    }
}
