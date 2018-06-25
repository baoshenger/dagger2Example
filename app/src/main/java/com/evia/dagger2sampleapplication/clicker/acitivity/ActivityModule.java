package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.clicker.fragment.FragmentModule;
import com.evia.dagger2sampleapplication.clicker.fragment.MainFragment;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelProviderKey;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
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
        return new ClickerResultMapper("Activity");
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
