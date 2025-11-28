package com.example.labmad;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpenseStorage {

    // !!! IMPORTANT: REPLACE THIS WITH YOUR ACTUAL GUID FROM POSTMAN !!!
    private static final String DB_GUID = "d6be9619-852e-4c17-ae4f-f77ee3d20646";
    private static final String BASE_URL = "https://expense-tracker-db-one.vercel.app/";

    private static ExpenseApiService apiService;

    public static ExpenseApiService getApiService() {
        if (apiService == null) {
            // Create an OkHttpClient to add the custom header
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("X-DB-NAME", DB_GUID)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set the custom client
                    .build();
            apiService = retrofit.create(ExpenseApiService.class);
        }
        return apiService;
    }
}