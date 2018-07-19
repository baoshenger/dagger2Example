package com.evia.dagger2sampleapplication.common.userinput;

import android.arch.lifecycle.ViewModel;

import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupport;
import com.evia.dagger2sampleapplication.common.presentationmodel.OnPropertyChangedListener;
import com.evia.dagger2sampleapplication.common.presentationmodel.PresentationModel;

/**
 * Created by Evgenii Iashin on 17.07.18.
 */
public abstract class PresentationModelWithUserInput<VM extends ViewModel, UserInput> extends PresentationModel<VM> {

    protected UserInput userInput;

    public PresentationModelWithUserInput(VM viewModel, GenericModelSupport genericModelSupport, OnPropertyChangedListener onPropertyChangedListener) {
        super(viewModel, genericModelSupport, onPropertyChangedListener);
    }

    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
        onUserInputUpdated();
    }

    public UserInput getUserInput() {
        return userInput;
    }

    /**
     *  Called once {@link UserInput} is updated to sync all relevant Views
     */
    protected abstract void onUserInputUpdated();
}
