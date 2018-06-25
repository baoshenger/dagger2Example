package com.evia.dagger2sampleapplication.common.di;

import android.app.Activity;

import com.evia.dagger2sampleapplication.clicker.acitivity.ActivityClickCounter;
import com.evia.dagger2sampleapplication.clicker.acitivity.MainActivity;
import com.evia.dagger2sampleapplication.clicker.fragment.MainFragment;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module
public interface ActivityModule {

    @Binds
    @Named("activity")
    BaseClickObservable bindGlovalClickCounter(ActivityClickCounter globalClickCounter);

    @Binds
    Activity bindActivity(MainActivity mainActivity);

    @FragmentScope
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    MainFragment mainFragment();

}
