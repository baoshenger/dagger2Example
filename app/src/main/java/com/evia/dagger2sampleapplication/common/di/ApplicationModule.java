package com.evia.dagger2sampleapplication.common.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.util.Log;

import com.evia.dagger2sampleapplication.clicker.acitivity.ScreenMainModule;
import com.evia.dagger2sampleapplication.clicker.global.GlobalModule;
import com.evia.dagger2sampleapplication.common.Logger;
import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupport;
import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupportImpl;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Module which contains classes accessible on the Application level
 *
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module(includes = {GlobalModule.class, ScreenMainModule.class})
public abstract class ApplicationModule {

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);

    @Provides
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("prefs", MODE_PRIVATE);
    }

    @Binds
    abstract GenericModelSupport bindGenericModelSupport(GenericModelSupportImpl modelSupport);

    @Provides
    @Singleton
    static Logger provideLogger() {
        return entry -> Log.e("LOGGER", entry);
    }
}
