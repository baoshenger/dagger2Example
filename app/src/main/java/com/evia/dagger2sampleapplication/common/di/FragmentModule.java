package com.evia.dagger2sampleapplication.common.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.evia.dagger2sampleapplication.clicker.fragment.FragmentClickCounter;
import com.evia.dagger2sampleapplication.clicker.fragment.MainFragmentViewModel;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelFactory;
import com.evia.dagger2sampleapplication.common.viewmodel.ViewModelProviderKey;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Evgenii Iashin on 02.02.18.
 */
@Module
public interface FragmentModule {

    @Binds
    @Named("fragment")
    BaseClickObservable bindsFragmentClickObservable(FragmentClickCounter fragmentClickCounter);

    @Binds
    ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelProviderKey(MainFragmentViewModel.class)
    ViewModel bindsMainFragmentViewModel(MainFragmentViewModel viewModel);
}
