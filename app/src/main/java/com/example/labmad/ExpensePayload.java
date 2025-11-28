package com.example.labmad;

/**
 * This class represents the data payload sent to the server when creating a new expense.
 * It only contains the fields that the client is responsible for providing.
 */
public class ExpensePayload {
    public final double amount;
    public final String currency;
    public final String category;
    public final String remark;
    public final String createdBy;

    public ExpensePayload(double amount, String currency, String category, String remark, String createdBy) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.createdBy = createdBy;
    }
}
