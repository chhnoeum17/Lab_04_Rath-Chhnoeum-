package com.example.labmad;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {
    public double amount;
    public String currency;
    public String category;
    public String remark;
    public String createdDate;

    public Expense() {}

    public Expense(double amount, String currency, String category, String remark, String date) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.createdDate = date;
    }

    protected Expense(Parcel in) {
        amount = in.readDouble();
        currency = in.readString();
        category = in.readString();
        remark = in.readString();
        createdDate = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(currency);
        dest.writeString(category);
        dest.writeString(remark);
        dest.writeString(createdDate);
    }
}
