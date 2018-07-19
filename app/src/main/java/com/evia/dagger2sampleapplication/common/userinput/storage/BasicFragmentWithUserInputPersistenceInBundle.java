package com.evia.dagger2sampleapplication.common.userinput.storage;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evia.dagger2sampleapplication.common.presentationmodel.BasicFragmentWithPresentationModel;
import com.evia.dagger2sampleapplication.common.userinput.PresentationModelWithUserInput;

import javax.inject.Inject;

/**
 * Created by Evgenii Iashin on 17.07.18.
 */
public abstract class BasicFragmentWithUserInputPersistenceInBundle<UserInput, VM extends ViewModel, P extends PresentationModelWithUserInput<VM, UserInput>> extends BasicFragmentWithPresentationModel<VM, P> {

    @Inject
    BundleStorage<UserInput> bundleStorage;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        presentationModel.setUserInput(getUserInput(savedInstanceState));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        bundleStorage.toBundle(presentationModel.getUserInput(), outState);
        super.onSaveInstanceState(outState);
    }

    /**
     *  Returns {@link UserInput} instance restored from the bundle or default if it can't be restored
     *
     * @param savedInstanceState - saved instance state which is used to persist {@link UserInput}
     * @return instance of {@link UserInput}
     */
    @NonNull
    protected UserInput getUserInput(@Nullable Bundle savedInstanceState) {
        UserInput result = null;
        if (savedInstanceState != null) {
            result = bundleStorage.fromBundle(savedInstanceState);
        }

        return result != null ? result : bundleStorage.getDefault();
    }
}
