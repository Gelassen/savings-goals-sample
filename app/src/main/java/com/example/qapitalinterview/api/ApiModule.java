package com.example.qapitalinterview.api;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.BuildConfig;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 10/30/2016.
 */

@Module
public class ApiModule {

    private static final String API = "http://qapital-ios-testtask.herokuapp.com";

    @Provides
    public static IApi getApiInterface() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(API);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.client(okHttpClient.build());

        return builder.build().create(IApi.class);
    }

    @Provides
    @Singleton
    @Named(App.Const.UI_THREAD)
    public static Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(App.Const.IO_THREAD)
    public static Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }
}
