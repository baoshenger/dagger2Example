package com.evia.dagger2sampleapplication.di;

import android.app.Activity;

import com.evia.dagger2sampleapplication.MainActivity;
import com.evia.dagger2sampleapplication.MainFragment;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.scope.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module
public abstract class ActivityModule {

    @Binds
    @Named("activity")
    abstract BaseClickObservable bindGlovalClickCounter(MainActivity.ActivityClickCounter globalClickCounter);

    @Binds
    abstract Activity bindActivity(MainActivity mainActivity);

    @FragmentScope
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract MainFragment mainFragment();

}
