package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.clicker.fragment.FragmentModule;
import com.evia.dagger2sampleapplication.clicker.fragment.MainFragment;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelFactory;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelProviderKey;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 *  Module for dependencies available on the activity level
 *
 * Created by Evgenii Iashin on 26.01.18.
 */
@Module
public abstract class ActivityModule {

    @Provides
    @Named("activity")
    static ClickerUseCase provideActivityUseCase(ActivityClickCounter clickCounter) {
        return new ClickerUseCase(clickCounter);
    }

    @Provides
    @Named("activity")
    static ClickerResultMapper provideMapper() {
        return new ClickerResultMapper<Integer, Void>("Activity");
    }

    @Provides
    @Named("retainedModel")
    static ActivityViewModel provideViewModel(MainActivity activity, ViewModelFactory factory) {
        return ViewModelProviders.of(activity, factory).get(ActivityViewModel.class);
    }

    @Binds
    abstract Activity bindActivity(MainActivity mainActivity);

    @FragmentScope
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract MainFragment mainFragment();

    @Binds
    @IntoMap
    @ViewModelProviderKey(ActivityViewModel.class)
    abstract ViewModel mapActivityViewModel(ActivityViewModel viewModel);

}
