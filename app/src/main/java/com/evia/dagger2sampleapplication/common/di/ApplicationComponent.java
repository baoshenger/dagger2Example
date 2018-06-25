package com.evia.dagger2sampleapplication.common.di;

import android.app.Application;

import com.evia.dagger2sampleapplication.SampleApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Evgenii Iashin on 26.01.18.
 */
@Singleton
@Component(modules = {ApplicationModule.class, AndroidInjectionModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder addAppContext(Application application);
        ApplicationComponent build();
    }

    void inject(SampleApplication application);
}
