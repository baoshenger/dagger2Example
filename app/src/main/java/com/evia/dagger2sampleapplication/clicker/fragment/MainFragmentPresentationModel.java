package com.evia.dagger2sampleapplication.clicker.fragment;

import android.arch.lifecycle.Observer;
import android.util.Log;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupport;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelAutoBinder;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelBinder;
import com.evia.dagger2sampleapplication.common.presentationmodel.PresentationModel;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Evgenii Iashin on 28.06.18.
 */
public class MainFragmentPresentationModel extends PresentationModel<MainFragmentViewModel> {

    @Inject
    public MainFragmentPresentationModel(@Named("retainedModel") MainFragmentViewModel viewModel, GenericModelSupport genericModelSupport, ModelBinder.BindingOnPropertyChangedListener onPropertyChangedListener) {
        super(viewModel, genericModelSupport, onPropertyChangedListener);
        Log.i(getClass().getSimpleName(), "instance created");
    }

    public Observer<CallStatus<String, Void>> observer = status -> {
        this.status = status;
        notifyDataChanged(PROP_COUNTER_PROGRESS);
        notifyDataChanged(PROP_COUNTER_TEXT);
    };

    private static final String PROP_COUNTER_PROGRESS = "PROP_COUNTER_PROGRESS";
    private static final String PROP_COUNTER_TEXT = "PROP_COUNTER_TEXT";

    //transient since it's gonna be updated after each orientation change
    private transient CallStatus<String, Void> status;

    @ModelAutoBinder.BindTextView(id = R.id.fragment_counter, property = PROP_COUNTER_TEXT)
    public String getCounterText() {
        return status.getCurrentState();
    }

    @ModelAutoBinder.BindOnClick(id = R.id.fragment_counter)
    public void onFragmentCounterClick() {
        viewModel.click();
    }

    @ModelAutoBinder.BindProgress(id = R.id.fragment_counter, property = PROP_COUNTER_PROGRESS)
    public boolean isProgressVisible() {
        return status instanceof CallStatus.Loading;
    }
}
