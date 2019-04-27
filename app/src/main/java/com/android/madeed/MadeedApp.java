package com.android.madeed;

import android.app.Application;
import android.content.Context;

public class MadeedApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MadeedApi getApi(Context context) {
        return MadeedApi.getInstance(context);
    }
}
