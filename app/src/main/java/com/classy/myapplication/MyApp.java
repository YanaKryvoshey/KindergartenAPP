package com.classy.myapplication;



import android.app.Application;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MyApp extends Application {

    public static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        String appID = "application-0-hktxy"; // replace this with your App ID
        app = new App(new AppConfiguration.Builder(appID)
                .appName("My App")
                .requestTimeout(30, TimeUnit.SECONDS)
                .build());


    }

}