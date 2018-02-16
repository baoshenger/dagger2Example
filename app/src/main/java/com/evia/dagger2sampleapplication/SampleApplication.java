package com.evia.dagger2sampleapplication;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.di.ApplicationComponent;
import com.evia.dagger2sampleapplication.di.DaggerApplicationComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Evgenii Iashin on 23.01.18.
 */
public class SampleApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationComponent applicationComponent = createApplicationComponent();
        applicationComponent.inject(this);
    }

    protected ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder().addAppContext(this).build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Singleton
    public static class GlobalClickCounter extends BaseClickObservable {

        @Inject
        public GlobalClickCounter(GlobalClickStorage globalClickStorage, Logger logger) {
            super("Global", globalClickStorage, logger);
        }
    }

    @Singleton
    public static class GlobalClickStorage implements ClickStorage {

        private static final String COUNT_KEY = "COUNT_KEY";

        private final SharedPreferences sharedPreferences;

        @Inject
        public GlobalClickStorage(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        public void storeClicks(int clicks) {
            sharedPreferences.edit().putInt(COUNT_KEY, clicks).apply();
        }

        @Override
        public int getClicks() {
            return sharedPreferences.getInt(COUNT_KEY, 0);
        }
    }
}
