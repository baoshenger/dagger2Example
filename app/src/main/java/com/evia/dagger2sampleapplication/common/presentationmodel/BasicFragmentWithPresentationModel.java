package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;

/**
 *  Basic fragment with support of {@link PresentationModel}
 *
 *  @param <P> - presentation model
 */
public abstract class BasicFragmentWithPresentationModel<VM extends ViewModel, P extends PresentationModel<VM>> extends Fragment {

    @Inject
    protected P presentationModel;

    @Inject
    @Named("retainedModel")
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    /**
     *  Returns layout resource id for
     *
     * @return layout resource id
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     *  Binds PresentationModel to Live Data sources provided by ViewModel
     *  NOTE: there is no need to remove observer manually, since it's done automatically according to the fragment lifecycle
     */
    protected abstract void bindPresentationModelToViewModel();

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindPresentationModelToViewModel();
        presentationModel.autoBind(view);
        //no need for initial synchronisation of binding since we'll receive initial update from the ViewModel
    }
}
