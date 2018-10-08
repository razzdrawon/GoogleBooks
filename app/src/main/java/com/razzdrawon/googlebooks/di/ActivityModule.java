package com.razzdrawon.googlebooks.di;

import com.razzdrawon.googlebooks.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    public abstract MainActivity contributesMainActivity ();

}
