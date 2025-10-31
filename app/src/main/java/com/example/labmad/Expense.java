package com.example.labmad;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Expense implements Parcelable, Serializable {

    public int id = 0;
    public final String amount;
    public final String currency;
    public final String category;
    public final String remark;
    public final String createdDate;

    public Expense(String amount,
                   @NonNull String currency,
                   @NonNull String category,
                   @NonNull String remark,
                   @NonNull String createdDate) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.createdDate = createdDate;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new ExpenseBuilder().setIn(in).createExpense();
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(Double.parseDouble(amount));
        dest.writeString(currency);
        dest.writeString(category);
        dest.writeString(remark);
        dest.writeString(createdDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "Expense{id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", category='" + category + '\'' +
                ", remark='" + remark + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}