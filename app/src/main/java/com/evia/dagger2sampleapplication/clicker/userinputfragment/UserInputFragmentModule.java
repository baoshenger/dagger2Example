package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.userinput.storage.BundleStorage;
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
public abstract class UserInputFragmentModule {

    @Provides
    @Named("retainedModel")
    static UserInputViewModel provideViewModel(UserInputFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(UserInputViewModel.class);
    }

    @Binds
    @IntoMap
    @ViewModelProviderKey(UserInputViewModel.class)
    abstract ViewModel bindsMainFragmentViewModel(UserInputViewModel viewModel);

    @Binds
    abstract BundleStorage<ClickUserInput> bindUserInputBundleStorage(UserInputStorage userInputStorage);

    @Module
    public interface FragmentModule {
        @FragmentScope
        @ContributesAndroidInjector(modules = {
                UserInputFragmentModule.class
        })
        UserInputFragment userInputFragment();
    }
}
