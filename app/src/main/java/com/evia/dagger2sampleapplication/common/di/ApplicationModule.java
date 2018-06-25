package com.evia.dagger2sampleapplication.common.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.evia.dagger2sampleapplication.Logger;
import com.evia.dagger2sampleapplication.clicker.acitivity.ActivityModule;
import com.evia.dagger2sampleapplication.clicker.acitivity.MainActivity;
import com.evia.dagger2sampleapplication.clicker.global.GlobalModule;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module(includes = {ViewModelsModule.class, GlobalModule.class})
public abstract class ApplicationModule {

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
