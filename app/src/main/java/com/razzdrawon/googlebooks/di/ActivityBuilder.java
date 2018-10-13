package com.razzdrawon.googlebooks.di;

import com.razzdrawon.googlebooks.activity.BookDetailsActivity;
import com.razzdrawon.googlebooks.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract BookDetailsActivity bindBookDetailsActivity();

}