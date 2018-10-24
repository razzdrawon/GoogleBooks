package com.razzdrawon.googlebooks.activity;

import com.razzdrawon.googlebooks.presenter.BookDetailsPresenter;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.BookDetailsView;
import dagger.Module;
import dagger.Provides;

@Module
public class BookDetailsActivityModule {

    @Provides
    BookDetailsView provideBookDetailsActivityView(BookDetailsActivity bookDetailsActivity){
        return bookDetailsActivity;
    }

    @Provides
    BookDetailsPresenter provideBookDetailsPresenter(BookDetailsView bookDetailsView, GoogleBooksService googleBooksService){
        return new BookDetailsPresenter(bookDetailsView, googleBooksService);
    }
}