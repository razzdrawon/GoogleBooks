package com.razzdrawon.googlebooks.di;

import android.app.Application;
import com.razzdrawon.googlebooks.GoogleBooksApp;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    GoogleBooksApp provideContext(Application application) {
        return (GoogleBooksApp) application;
    }
}
