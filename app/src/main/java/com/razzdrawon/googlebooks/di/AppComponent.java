package com.razzdrawon.googlebooks.di;

import com.razzdrawon.googlebooks.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class, NetworkModule.class})
public interface AppComponent {

        void inject(MainActivity mainActivity);

}

