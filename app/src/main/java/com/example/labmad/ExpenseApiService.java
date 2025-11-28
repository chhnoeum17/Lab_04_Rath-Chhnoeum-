package com.example.labmad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ExpenseApiService {
    @GET("expenses")
    Call<List<Expense>> getExpenses();

    @POST("expenses")
    Call<Expense> addExpense(@Body Expense expense);
}