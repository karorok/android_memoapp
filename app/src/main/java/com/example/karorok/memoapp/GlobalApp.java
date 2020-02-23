package com.example.karorok.memoapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GlobalApp extends Application {
    private static GlobalApp instance;

    public static GlobalApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new GlobalApp();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm2.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }


}
