package com.razzdrawon.googlebooks.di;

import com.razzdrawon.googlebooks.activity.BookDetailsActivity;
import com.razzdrawon.googlebooks.activity.BookDetailsActivityModule;
import com.razzdrawon.googlebooks.activity.MainActivity;
import com.razzdrawon.googlebooks.activity.MainActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector (modules = {NetworkModule.class, MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector (modules = {NetworkModule.class, BookDetailsActivityModule.class})
    abstract BookDetailsActivity bindBookDetailsActivity();

}