package com.evia.dagger2sampleapplication.common.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 *  General ViewModel factory which holds providers to build exact ViewModel instances once needed
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @SuppressWarnings("unchecked") //we're controlling this using generics, so fine
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final Provider<? extends ViewModel> viewModelProvider = creators.get(modelClass);
        if (viewModelProvider != null) {
            return (T) viewModelProvider.get();
        } else {
            throw new RuntimeException("There is no provider for " + modelClass.getSimpleName());
        }
    }
}
