package com.razzdrawon.googlebooks.presenter;

import com.razzdrawon.googlebooks.model.BookResponse;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.MainActivityView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter {

    private final MainActivityView view;
    public GoogleBooksService service;

    public MainActivityPresenter(MainActivityView view, GoogleBooksService service) {
        this.view = view;
        this.service = service;
    }

    public void getBoolList(String query, Integer startIndex, Integer maxResults) {
        view.showWait();

        service.getBooks(query, startIndex, maxResults)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.removeWait();
                        view.onAPIFailure();
                    }

                    @Override
                    public void onNext(BookResponse bookResponse) {
                        view.removeWait();
                        view.getBookResponseSuccess(bookResponse);
                    }
                });
    }
}
