package com.midwestpilotcars.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.models.addDayExpenses.AddDayExpenses;
import com.midwestpilotcars.models.getAllJobs.COMPLETE;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.getAllJobs.UPCOMING;
import com.midwestpilotcars.views.activities.BackTrip;
import com.midwestpilotcars.views.activities.CompletedJobActivity;
import com.midwestpilotcars.views.activities.EndForm;
import com.midwestpilotcars.views.activities.EndFormViewActivity;
import com.midwestpilotcars.views.activities.ExpenseDayAdd;
import com.midwestpilotcars.views.activities.ViewCompleteJob;
import com.midwestpilotcars.views.activities.ViewJobActivity;
import com.midwestpilotcars.views.startJob.StartJobActivity;

import java.util.ArrayList;

public class NewJobsAdapter extends RecyclerView.Adapter<NewJobsAdapter.ViewHolder> {
    private ArrayList<UPCOMING> upcomingJobModel;
    private ArrayList<ONGOING> ongoingArrayList;
    private ArrayList<COMPLETE> completeArrayList;
    private int jobType = 0;

    public NewJobsAdapter(ArrayList model, int jobType) {
        this.jobType = jobType;
        if (jobType == 0)
            upcomingJobModel = model;
        else if (jobType == 1)
            ongoingArrayList = model;
        else if (jobType == 2)
            completeArrayList = model;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView logisticName;
        View logisticImage;
        TextView carName;
        View carImage;

        ViewHolder(View view) {
            super(view);
            logisticName = view.findViewById(R.id.courier_name);
            logisticImage = view.findViewById(R.id.courier_image);
            carName = view.findViewById(R.id.car_name);
            carImage = view.findViewById(R.id.new_job_car);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (jobType == 0) {
                Intent intent = new Intent(v.getContext(), StartJobActivity.class);
                intent.putExtra(AppConstants.JOBS_UPCOMING, upcomingJobModel.get(getPosition()));
                v.getContext().startActivity(intent);
            } else if (jobType == 1) {
                    String end_trip_status = ongoingArrayList.get(getPosition()).getEndTripStatus();
                                    if(end_trip_status.equals("1")){
                    Intent intent = new Intent(v.getContext(), EndForm.class);
                    intent.putExtra(AppConstants.JOB_KEY,ongoingArrayList.get(getPosition()).getJobId());
                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList.get(getPosition()));
                    intent.putExtra(AppConstants.END_STATUS, end_trip_status);
                    v.getContext().startActivity(intent);
                }else if(end_trip_status.equals("2")){
                 //   Intent intent = new Intent(v.getContext(), EndForm.class);
                    Intent intent = new Intent(v.getContext(), EndFormViewActivity.class);
                    intent.putExtra(AppConstants.JOB_KEY,ongoingArrayList.get(getPosition()).getJobId());
                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList.get(getPosition()));
                    intent.putExtra(AppConstants.END_STATUS, end_trip_status);
                    v.getContext().startActivity(intent);
                }else if(end_trip_status.equals("3")){

                    Intent intent = new Intent(v.getContext(), BackTrip.class);
                    intent.putExtra(AppConstants.JOB_KEY,ongoingArrayList.get(getPosition()).getJobId());

                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList.get(getPosition()));
                    intent.putExtra(AppConstants.END_STATUS, end_trip_status);
                    v.getContext().startActivity(intent);
                }
                else{
                    Intent intent = new Intent(v.getContext(), ExpenseDayAdd.class);
                    intent.putExtra(AppConstants.JOB_KEY,ongoingArrayList.get(getPosition()).getJobId());
                    intent.putExtra(AppConstants.DAY_FILLED, ongoingArrayList.get(getPosition()).getDaysFilled());
                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList.get(getPosition()));
                    v.getContext().startActivity(intent);
                }

            }else if (jobType == 2) {
                Intent intent = new Intent(v.getContext(), CompletedJobActivity.class);
                intent.putExtra(AppConstants.JOBS_COMPLETE, completeArrayList.get(getPosition()));
                intent.putExtra(AppConstants.JOB_KEY,completeArrayList.get(getPosition()).getJobId());
                v.getContext().startActivity(intent);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_job, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (jobType == 0) {
            viewHolder.carImage.setBackgroundResource(R.mipmap.new_job_car);
            viewHolder.logisticName.setText(upcomingJobModel.get(i).getTruckingCompanyName());
            viewHolder.carName.setText(upcomingJobModel.get(i).getLoadTypeName()+"\n"+"Start Date: "+upcomingJobModel.get(i).getJobStartDate());
        } else if (jobType == 1) {
            viewHolder.carImage.setBackgroundResource(R.mipmap.ongoing_car);
            viewHolder.logisticName.setText(ongoingArrayList.get(i).getTruckingCompanyName());
            viewHolder.carName.setText(ongoingArrayList.get(i).getLoadTypeName()+"\n"+"Start Date: "+ongoingArrayList.get(i).getJobStartDate());
        } else if (jobType == 2) {
            viewHolder.carImage.setBackgroundResource(R.mipmap.completed_car);
            viewHolder.logisticName.setText(completeArrayList.get(i).getTruckingCompanyName());
            viewHolder.carName.setText(completeArrayList.get(i).getLoadTypeName()+"\n"+"Start Date: "+completeArrayList.get(i).getJobStartDate());
        }

    }

    @Override
    public int getItemCount() {
        if (jobType == 0) {
            return upcomingJobModel.size();
        } else if (jobType == 1) {
            return ongoingArrayList.size();
        } else if (jobType == 2) {
            return completeArrayList.size();
        }
        return 0;
    }
}