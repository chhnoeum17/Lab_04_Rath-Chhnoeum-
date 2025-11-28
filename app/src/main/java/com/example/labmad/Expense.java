package com.example.labmad;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Expense implements Parcelable, Serializable {

    public final String id;
    public final double amount;
    public final String currency;
    public final String category;
    public final String remark;
    public final String createdBy;
    public final String createdDate;

    public Expense(String id, double amount, String currency, String category, String remark, String createdBy, String createdDate) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    protected Expense(Parcel in) {
        id = in.readString();
        amount = in.readDouble();
        currency = in.readString();
        category = in.readString();
        remark = in.readString();
        createdBy = in.readString();
        createdDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(amount);
        dest.writeString(currency);
        dest.writeString(category);
        dest.writeString(remark);
        dest.writeString(createdBy);
        dest.writeString(createdDate);
    }

    @Override
    public int describeContents() {
        return 0;
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
}