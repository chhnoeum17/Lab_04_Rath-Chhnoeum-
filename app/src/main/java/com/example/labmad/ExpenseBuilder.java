package com.example.labmad;

public class ExpenseBuilder {
    private String id;
    private double amount;
    private String currency;
    private String category;
    private String remark;
    private String createdBy;
    private String createdDate;

    public ExpenseBuilder setId(String id) {
        this.id = id;
        return this;
    }

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

    public ExpenseBuilder setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ExpenseBuilder setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Expense createExpense() {
        return new Expense(id, amount, currency, category, remark, createdBy, createdDate);
    }
}