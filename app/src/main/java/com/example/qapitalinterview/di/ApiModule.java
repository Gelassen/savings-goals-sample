package com.example.qapitalinterview.di;

import android.content.Context;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.BuildConfig;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;

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

@Module
public class ApiModule {

    private static final String API = "http://qapital-ios-testtask.herokuapp.com";

    private Context context;

    public ApiModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    /*package*/ IApi getApiInterface() {
        return getApiInterface(API);
    }

    @Provides
    @Singleton
    /*package*/ IModel getModel() {
        return new Model(context);
    }

    public static IApi getApiInterface(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.client(okHttpClient.build());

        return builder.build().create(IApi.class);
    }

    @Provides
    @Singleton
    @Named(App.Const.UI_THREAD)
    /*package*/ static Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(App.Const.IO_THREAD)
    /*package*/ static Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }
}
