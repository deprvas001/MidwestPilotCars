package com.midwestpilotcars.models.addDayExpenses;

public class DayMotelExpense {
    private String expense_mode;
    private String expense_comment;
    private String expense_price;
    private String expense_image;
    private String expense_type;

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public DayMotelExpense(){

    }

    public String getExpense_mode() {
        return expense_mode;
    }

    public void setExpense_mode(String expense_mode) {
        this.expense_mode = expense_mode;
    }

    public String getExpense_comment() {
        return expense_comment;
    }

    public void setExpense_comment(String expense_comment) {
        this.expense_comment = expense_comment;
    }

    public String getExpense_price() {
        return expense_price;
    }

    public void setExpense_price(String expense_price) {
        this.expense_price = expense_price;
    }

    public String getExpense_image() {
        return expense_image;
    }

    public void setExpense_image(String expense_image) {
        this.expense_image = expense_image;
    }
}
