package com.example.labmad;

import android.os.Parcel;

public class ExpenseBuilder {
    private int id;
    private double amount;
    private String currency;
    private String category;
    private String remark;
    private String createdDate;

    public ExpenseBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public ExpenseBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ExpenseBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public ExpenseBuilder setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public ExpenseBuilder setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public ExpenseBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ExpenseBuilder setIn(Parcel in) {
        id = in.readInt();
        amount = in.readDouble();
        currency = in.readString();
        category = in.readString();
        remark = in.readString();
        createdDate = in.readString();
        return this;
    }

    public Expense createExpense() {
        Expense expense = new Expense(String.valueOf(amount), currency, category, remark, createdDate);
        expense.id = this.id;
        return expense;
    }
}