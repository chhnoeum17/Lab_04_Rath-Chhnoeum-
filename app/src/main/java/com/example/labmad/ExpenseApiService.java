package com.example.labmad;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseApiService {

    @GET("expenses")
    Call<List<Expense>> getExpenses();

    @POST("expenses")
    Call<Expense> addExpense(@Body ExpensePayload payload);

    @DELETE("expenses/{id}")
    Call<ResponseBody> deleteExpense(@Path("id") String id);

    @PUT("expenses/{id}")
    Call<Expense> updateExpense(@Path("id") String id, @Body Expense expense);
}