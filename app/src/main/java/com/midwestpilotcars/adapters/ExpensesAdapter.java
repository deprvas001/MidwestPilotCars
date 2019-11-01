package com.midwestpilotcars.adapters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.midwestpilotcars.R;
import com.midwestpilotcars.callbacks.ImageClickCallBack;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.databinding.LayoutGasExpensesBinding;
import com.midwestpilotcars.enums.ExpensesEnum;
import com.midwestpilotcars.models.addDayExpenses.AddDayExpenses;
import com.midwestpilotcars.models.addDayExpenses.GasExpensesModel;
import com.midwestpilotcars.models.addDayExpenses.MotelExpensesModel;
import com.midwestpilotcars.models.addDayExpenses.OtherExpensesModel;
import com.midwestpilotcars.models.getAllJobs.COMPLETE;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.getAllJobs.UPCOMING;
import com.midwestpilotcars.views.activities.ViewJobActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {
    private ArrayList<GasExpensesModel> gasExpensesModelArrayList;
    private ArrayList<GasExpensesModel> motelExpensesModelArrayList;
    private ArrayList<GasExpensesModel> otherExpensesModelArrayList;
    private ExpensesEnum expensesType;
    private ImageClickCallBack imageClickCallBack;
    private int position = 0;
    private LayoutGasExpensesBinding layoutGasExpensesBinding;

    public ExpensesAdapter(@NotNull ArrayList gasExpenes, @NotNull ExpensesEnum expensesType, ImageClickCallBack imageClickCallBack) {
        this.expensesType = expensesType;
        this.imageClickCallBack = imageClickCallBack;
        if (expensesType == ExpensesEnum.GAS)
            gasExpensesModelArrayList = gasExpenes;
        else if (expensesType == ExpensesEnum.MOTEL)
            motelExpensesModelArrayList = gasExpenes;
        else if (expensesType == ExpensesEnum.OTHER)
            otherExpensesModelArrayList = gasExpenes;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        EditText logisticName;
        ImageView cameraImage;
        TextView textViewComment;
        RadioButton optionCash;
        RadioButton optionCard;
        EditText editTextComment;

        ViewHolder(View view) {
            super(view);

            logisticName = view.findViewById(R.id.et_motel_expenses);
            logisticName.setHint(view.getContext().getString(R.string.gas_expenses));
            cameraImage = view.findViewById(R.id.img_camera_motel);
            textViewComment = view.findViewById(R.id.tv_motel_comment);
            optionCash = view.findViewById(R.id.option_cash_motel);
            optionCard = view.findViewById(R.id.option_card_motel);
            editTextComment = view.findViewById(R.id.et_motel_comment);
            textViewComment.setOnClickListener(this);
            cameraImage.setOnClickListener(this);
            optionCard.setOnCheckedChangeListener(this);
            optionCash.setOnCheckedChangeListener(this);
            layoutGasExpensesBinding.setExpenses(currentModel());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_motel_comment:
                    editTextComment.setVisibility(View.VISIBLE);
                    break;
                case R.id.img_camera_motel:
                    imageClickCallBack.clickImage(getPosition(), expensesType);
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.option_card_motel:
                    currentModel().setExpenseMode(2L);
                    break;
                case R.id.option_cash_motel:
                    currentModel().setExpenseMode(1L);
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    /*   View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_expenses, viewGroup, false);*/

        layoutGasExpensesBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_gas_expenses, viewGroup, false);

        if (layoutGasExpensesBinding!= null) {
            return new ViewHolder(layoutGasExpensesBinding.getRoot());
        } else {
            throw new IllegalStateException("Binding cannot be null. Perform binding before calling getBinding()");
        }
   // layoutGasExpensesBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_expenses, viewGroup, false);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        position = i;
    }

    @Override
    public int getItemCount() {
        if (expensesType == ExpensesEnum.GAS) {
            return gasExpensesModelArrayList.size();
        } else if (expensesType == ExpensesEnum.MOTEL) {
            return motelExpensesModelArrayList.size();
        } else if (expensesType == ExpensesEnum.OTHER) {
            return otherExpensesModelArrayList.size();
        }
        return 0;
    }

    private GasExpensesModel currentModel() {
        if (expensesType == ExpensesEnum.GAS) {
            return gasExpensesModelArrayList.get(position);
        } else if (expensesType == ExpensesEnum.MOTEL) {
            return motelExpensesModelArrayList.get(position);
        } else if (expensesType == ExpensesEnum.OTHER) {
            return otherExpensesModelArrayList.get(position);
        }
        return null;
    }
}