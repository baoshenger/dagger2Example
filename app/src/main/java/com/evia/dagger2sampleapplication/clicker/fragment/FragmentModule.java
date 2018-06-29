package com.evia.dagger2sampleapplication.clicker.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelFactory;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelProviderKey;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

/**
 *  Module for dependencies available on the fragment level
 *
 * Created by Evgenii Iashin on 02.02.18.
 */
@Module
public abstract class FragmentModule {

    @Provides
    @Named("retainedModel")
    static MainFragmentViewModel provideViewModel(Fragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(MainFragmentViewModel.class);
    }

    @Binds
    abstract Fragment bindFragment(MainFragment fragment);

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
}
