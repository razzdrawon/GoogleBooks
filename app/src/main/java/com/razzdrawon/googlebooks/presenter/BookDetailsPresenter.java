package com.razzdrawon.googlebooks.presenter;

import com.razzdrawon.googlebooks.model.Book;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.BookDetailsView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookDetailsPresenter {
    private final BookDetailsView view;
    public GoogleBooksService service;

    public BookDetailsPresenter(BookDetailsView view, GoogleBooksService service) {
        this.view = view;
        this.service = service;
    }

    public void getBookDetails(String bookId) {
        view.showWait();

        service.getBookById(bookId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.removeWait();
                        view.onAPIFailure();
                    }

                    @Override
                    public void onNext(Book book) {
                        view.removeWait();
                        view.getBookDetailsSuccess(book);
                    }
                });
    }
}
