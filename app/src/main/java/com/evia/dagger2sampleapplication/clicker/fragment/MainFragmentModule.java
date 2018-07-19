package com.evia.dagger2sampleapplication.clicker.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
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
 * Module for dependencies available on the fragment level
 * <p>
 * Created by Evgenii Iashin on 02.02.18.
 */
@Module
public abstract class MainFragmentModule {

    @Provides
    @Named("retainedModel")
    static MainFragmentViewModel provideViewModel(MainFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(MainFragmentViewModel.class);
    }

    @Provides
    @Named("fragment")
    static ClickerUseCase provideFragmentUseCase(FragmentClickCounter clickCounter) {
        return new ClickerUseCase(clickCounter);
    }

    @Provides
    @Named("fragment")
    static ClickerResultMapper provideMapper() {
        return new ClickerResultMapper("Fragment");
    }

    @Binds
    @IntoMap
    @ViewModelProviderKey(MainFragmentViewModel.class)
    abstract ViewModel bindsMainFragmentViewModel(MainFragmentViewModel viewModel);

    @Module
    public interface FragmentModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = {
                MainFragmentModule.class
        })
        MainFragment mainFragment();
    }

}
