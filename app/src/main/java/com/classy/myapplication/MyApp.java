package com.classy.myapplication;



import android.app.Application;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MyApp extends Application {

    public static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        String appID = "application-0-iamfa"; // replace this with your App ID
        app = new App(new AppConfiguration.Builder(appID).build());


    }

}