package com.razzdrawon.googlebooks.activity;

import com.razzdrawon.googlebooks.presenter.MainActivityPresenter;
import com.razzdrawon.googlebooks.services.GoogleBooksService;
import com.razzdrawon.googlebooks.view.MainActivityView;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainActivityView provideMainActivityView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainActivityPresenter provideMainActivityPresenter(MainActivityView mainActivityView, GoogleBooksService googleBooksService){
        return new MainActivityPresenter(mainActivityView, googleBooksService);
    }
}
