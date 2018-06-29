package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.arch.lifecycle.Observer;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupport;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelAutoBinder;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelBinder;
import com.evia.dagger2sampleapplication.common.presentationmodel.PresentationModel;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Evgenii Iashin on 29.06.18.
 */
public class MainActivityPresentationModel extends PresentationModel<ActivityViewModel> {

    @Inject
    public MainActivityPresentationModel(@Named("retainedModel") ActivityViewModel viewModel, GenericModelSupport genericModelSupport, ModelBinder.BindingOnPropertyChangedListener onPropertyChangedListener) {
        super(viewModel, genericModelSupport, onPropertyChangedListener);
    }

    public Observer<CallStatus<String, Void>> globalClickObserver = status -> {
        this.globalStatus = status;
        notifyDataChanged(PROP_COUNTER_PROGRESS_GLOBAL);
        notifyDataChanged(PROP_COUNTER_TEXT_GLOBAL);
    };

    public Observer<CallStatus<String, Void>> activityClickObserver = status -> {
        this.activityStatus = status;
        notifyDataChanged(PROP_COUNTER_PROGRESS_ACTIVITY);
        notifyDataChanged(PROP_COUNTER_TEXT_ACTIVITY);
    };

    private static final String PROP_COUNTER_PROGRESS_GLOBAL = "PROP_COUNTER_PROGRESS_GLOBAL";
    private static final String PROP_COUNTER_TEXT_GLOBAL = "PROP_COUNTER_TEXT_GLOBAL";

    private static final String PROP_COUNTER_PROGRESS_ACTIVITY = "PROP_COUNTER_PROGRESS_ACTIVITY";
    private static final String PROP_COUNTER_TEXT_ACTIVITY = "PROP_COUNTER_TEXT_ACTIVITY";

    //transient since it's gonna be updated after each orientation change
    private transient CallStatus<String, Void> globalStatus;
    private transient CallStatus<String, Void> activityStatus;

    @ModelAutoBinder.BindTextView(id = R.id.global_counter, property = PROP_COUNTER_TEXT_GLOBAL)
    public String getGlobalCounterText() {
        return globalStatus.getCurrentState();
    }

    @ModelAutoBinder.BindOnClick(id = R.id.global_counter)
    public void onGlobalCounterClick() {
        viewModel.clickGlobalClicker();
    }

    @ModelAutoBinder.BindProgress(id = R.id.global_counter, property = PROP_COUNTER_PROGRESS_GLOBAL)
    public boolean isGlobalProgressVisible() {
        return globalStatus instanceof CallStatus.Loading;
    }

    @ModelAutoBinder.BindTextView(id = R.id.activity_counter, property = PROP_COUNTER_TEXT_ACTIVITY)
    public String getActivityCounterText() {
        return activityStatus.getCurrentState();
    }

    @ModelAutoBinder.BindOnClick(id = R.id.activity_counter)
    public void onActivityCounterClick() {
        viewModel.clickActivityClicker();
    }

    @ModelAutoBinder.BindProgress(id = R.id.activity_counter, property = PROP_COUNTER_PROGRESS_ACTIVITY)
    public boolean isActivityProgressVisible() {
        return activityStatus instanceof CallStatus.Loading;
    }

}
