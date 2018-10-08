package com.razzdrawon.googlebooks.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";

    // Keep your services here, build them in buildRetrofit method later
    private GoogleBooksService service;

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }

    // Build retrofit once when creating a single instance
    private RetrofitClient() {
        buildRetrofit(BASE_URL);
    }

    private void buildRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();;

        this.service = retrofit.create(GoogleBooksService.class);
    }

    public GoogleBooksService getService() {
        return this.service;
    }
}
