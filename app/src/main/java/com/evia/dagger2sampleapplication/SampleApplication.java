package com.evia.dagger2sampleapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;

/**
 * Created by Evgenii Iashin on 23.01.18.
 */

public class SampleApplication extends Application /*implements HasActivityInjector */{

//    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
    }

//    @Override
//    public AndroidInjector<Activity> activityInjector() {
//        return dispatchingAndroidInjector;
//    }

    public static class GlobalClickCounter extends BaseClickObservable {

        private static GlobalClickCounter instance;

        public static synchronized GlobalClickCounter getInstance(Application application) {
            if (instance == null) {
                instance = new GlobalClickCounter(application.getSharedPreferences("prefs", MODE_PRIVATE));
            }
            return instance;
        }

        private final SharedPreferences prefs;

        private GlobalClickCounter(SharedPreferences prefs) {
            /*
                some code here to init something globally accessible
             */
            super("Global", prefs.getInt("COUNT", 0));
            this.prefs = prefs;
        }

        @Override
        public void countClick() {
            super.countClick();
            prefs.edit().putInt("COUNT", clickCount).apply();
        }
    }
}
