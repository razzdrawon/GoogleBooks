package com.razzdrawon.googlebooks;

import com.razzdrawon.googlebooks.di.AppComponent;
import com.razzdrawon.googlebooks.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class GoogleBooksApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent component = DaggerAppComponent
                .builder()
                .application(this)
                .build();
        return component;
    }

}
