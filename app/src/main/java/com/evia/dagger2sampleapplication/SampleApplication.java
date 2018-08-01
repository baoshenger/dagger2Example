package com.evia.dagger2sampleapplication;

import android.app.Activity;
import android.app.Application;

import com.evia.dagger2sampleapplication.common.di.ApplicationComponent;
import com.evia.dagger2sampleapplication.common.di.DaggerApplicationComponent;

import javax.inject.Inject;

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
      //  return DaggerApplicationComponent.builder().addAppContext(this).build();

        return  DaggerApplicationComponent.builder().addAppContext(this).build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
