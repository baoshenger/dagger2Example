package com.evia.dagger2sampleapplication.di;

import com.evia.dagger2sampleapplication.MainFragment;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Evgenii Iashin on 02.02.18.
 */
@Module
public interface FragmentModule {

    @Binds
    @Named("fragment")
    BaseClickObservable bindsFragmentClickObservable(MainFragment.FragmentClickCounter fragmentClickCounter);
}
