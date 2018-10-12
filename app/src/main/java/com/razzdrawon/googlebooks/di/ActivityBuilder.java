package com.razzdrawon.googlebooks.di;

import com.razzdrawon.googlebooks.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector (modules = NetworkModule.class)
    abstract MainActivity bindMainActivity();

}