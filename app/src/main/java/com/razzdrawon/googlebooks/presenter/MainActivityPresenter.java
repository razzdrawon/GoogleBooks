package com.razzdrawon.googlebooks.presenter;

import com.razzdrawon.googlebooks.model.BookResponse;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.services.RetrofitClient;
import com.razzdrawon.googlebooks.view.MainActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private final MainActivityView view;
    public GoogleBooksService service;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
        service = RetrofitClient.getInstance().getService();
    }

    public void getBoolList(String query, Integer startIndex, Integer maxResults) {
        view.showWait();
        service.getBooks(query, startIndex, maxResults).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {

                if (response.isSuccessful()) {
                    view.removeWait();
                    view.getBookResponseSuccess(response.body());
                }
                else {
                    // error case
                    switch (response.code()) {
                        default:
                            view.removeWait();
                            view.onAPIFailure();
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                view.removeWait();
                view.onAPIFailure();
            }
        });

    }



}
