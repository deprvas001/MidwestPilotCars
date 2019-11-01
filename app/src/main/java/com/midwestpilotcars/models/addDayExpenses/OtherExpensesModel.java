
package com.midwestpilotcars.models.addDayExpenses;

import com.google.gson.annotations.SerializedName;

public class OtherExpensesModel {

    @SerializedName("expense_comment")
    private String expenseComment;
    @SerializedName("expense_image")
    private String expenseImage;
    @SerializedName("expense_mode")
    private Long expenseMode;
    @SerializedName("expense_price")
    private String expensePrice;

    public String getExpenseComment() {
        return expenseComment;
    }

    public void setExpenseComment(String expenseComment) {
        this.expenseComment = expenseComment;
    }

    public String getExpenseImage() {
        return expenseImage;
    }

    public void setExpenseImage(String expenseImage) {
        this.expenseImage = expenseImage;
    }

    public Long getExpenseMode() {
        return expenseMode;
    }

    public void setExpenseMode(Long expenseMode) {
        this.expenseMode = expenseMode;
    }

    public String getExpensePrice() {
        return expensePrice;
    }

    public void setExpensePrice(String expensePrice) {
        this.expensePrice = expensePrice;
    }

}
