package com.midwestpilotcars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;
import com.midwestpilotcars.models.wallet.AllMoneyRequest;

import java.util.List;

public class AllWalletAdapter extends RecyclerView.Adapter<AllWalletAdapter.MyViewHolder> {

    private List<AllMoneyRequest> day_List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView wallet_amount,wallet_comment,wallet_driver_id,wallet_created,wallet_id,status_id;

        public MyViewHolder(View view) {
            super(view);
            wallet_amount = (TextView) view.findViewById(R.id.wallet_amount);
            wallet_comment = (TextView)view.findViewById(R.id.wallet_comment);
            wallet_driver_id = (TextView)view.findViewById(R.id.wallet_driver_id);
            wallet_created = (TextView)view.findViewById(R.id.wallet_created_on);
            wallet_id = (TextView)view.findViewById(R.id.wallet_id);
            status_id = (TextView)view.findViewById(R.id.status_id);
        }
    }


    public AllWalletAdapter(List<AllMoneyRequest> day_List) {
        this.day_List = day_List;
    }

    @Override
    public AllWalletAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_wallet_details, parent, false);

        return new AllWalletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllWalletAdapter.MyViewHolder holder, int position) {
        AllMoneyRequest day = day_List.get(position);
        if(day.getWallet_amount() !=null){
            holder.wallet_amount.setText("Cash In Advance Request - "+"$"+day.getWallet_amount());
        }else {
            holder.wallet_amount.setText("Cash In Advance Request - $0");
        }
        if(day.getWallet_comment() !=null){
            holder.wallet_comment.setText("Wallet Comments - "+day.getWallet_comment());
        }else{
            holder.wallet_comment.setText("Wallet Comments - ");
        }

        if(day.getDriver_id() !=null){
            holder.wallet_driver_id.setText("Wallet Driver Id - "+day.getDriver_id());
        }else{
            holder.wallet_driver_id.setText("Wallet Driver Id - ");
        }
        if(day.getCreated_on() !=null){
            holder.wallet_created.setText("Wallet Created On - "+day.getCreated_on());
        }

        if(day.getWallet_id() !=null){
            holder.wallet_id.setText("Wallet Id - "+day.getWallet_id());
        }

        String status = day.getStatus();
        if(status.equals("1")){
            holder.status_id.setText("Status - "+"Pending from admin");
        }else if (status.equals("2")){
            holder.status_id.setText("Status - "+"Approved by admin");
        }
    }

    @Override
    public int getItemCount() {
        return day_List.size();
    }
}
