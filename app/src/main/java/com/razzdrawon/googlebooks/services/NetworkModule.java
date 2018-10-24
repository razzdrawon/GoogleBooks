package com.razzdrawon.googlebooks.services;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";

    @Provides
    public Converter.Factory providesGson(){
        return GsonConverterFactory.create();
    }

    @Provides
    public CallAdapter.Factory providesRxJava(){
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    public Retrofit providesRetrofit(CallAdapter.Factory rxJava, Converter.Factory gsonConverter){
        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJava)
                .addConverterFactory(gsonConverter)
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    public GoogleBooksService providesService(Retrofit retrofit){
        return retrofit.create(GoogleBooksService.class);
    }
}
