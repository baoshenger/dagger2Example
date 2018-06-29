package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.evia.dagger2sampleapplication.common.di.BasicActivityWithDiSupport;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Evgenii Iashin on 29.06.18.
 */
public abstract class BasicActivityWithPresentationModel<VM extends ViewModel, P extends PresentationModel<VM>> extends BasicActivityWithDiSupport {

    @Inject
    protected P presentationModel;

    @Inject
    @Named("retainedModel")
    protected VM viewModel;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = LayoutInflater.from(this).inflate(getLayoutRes(), null);
        setContentView(rootView);
        bindPresentationModelToViewModel();
        presentationModel.autoBind(rootView);
    }
}
