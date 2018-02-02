package com.evia.dagger2sampleapplication.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.evia.dagger2sampleapplication.Logger;
import com.evia.dagger2sampleapplication.MainActivity;
import com.evia.dagger2sampleapplication.SampleApplication;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.scope.ActivityScope;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module
public abstract class ApplicationModule {

    @Binds
    @Named("global")
    abstract BaseClickObservable bindGlovalClickCounter(SampleApplication.GlobalClickCounter globalClickCounter);

    @Provides
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("prefs", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    static Logger provideLogger() {
        return entry -> Log.e("LOGGER", entry);
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = {ActivityModule.class})
    abstract MainActivity mainActivity();
}
