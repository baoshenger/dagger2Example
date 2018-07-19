package com.evia.dagger2sampleapplication.clicker.acitivity;

import com.evia.dagger2sampleapplication.clicker.fragment.MainFragmentModule;
import com.evia.dagger2sampleapplication.clicker.userinputfragment.UserInputFragmentModule;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Evgenii Iashin on 18.07.18.
 */
@Module
public interface ScreenMainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            UserInputFragmentModule.FragmentModule.class,
            MainFragmentModule.FragmentModule.class
    })
    MainActivity mainActivity();
}
