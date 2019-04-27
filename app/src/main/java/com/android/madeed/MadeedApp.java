package com.android.madeed;

import android.app.Application;
import android.content.Context;

public class MadeedApp extends Application {

    private static Context sInstance = null;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }

    public static Context getContext() {
        return sInstance;
    }

    public static MadeedApi getApi(Context context) {
        return MadeedApi.getInstance(context);
    }
}
