package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.global.GlobalClickLiveData;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *  ViewModel for the main activity
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@ActivityScope
public class ActivityViewModel extends ViewModel {

    private final ActivityClickLiveData activityClickLiveData;
    private final GlobalClickLiveData globalClickLiveData;

    public final LiveData<CallStatus<String, Void>> activityClickLiveDataMapped;
    public final LiveData<CallStatus<String, Void>> globalClickLiveDataMapped;

    @Inject
    public ActivityViewModel(ActivityClickLiveData activityClickLiveData,
                             @Named("activity") ClickerResultMapper activityMapper,
                             GlobalClickLiveData globalClickLiveData,
                             @Named("global") ClickerResultMapper globalMapper) {
        this.activityClickLiveData = activityClickLiveData;
        this.globalClickLiveData = globalClickLiveData;
        activityClickLiveDataMapped = Transformations.map(activityClickLiveData, (Function<CallStatus<Integer, Void>, CallStatus<String, Void>>) activityMapper::map);
        globalClickLiveDataMapped = Transformations.map(globalClickLiveData, (Function<CallStatus<Integer, Void>, CallStatus<String, Void>>) globalMapper::map);
    }

    public void clickActivityClicker() {
        activityClickLiveData.execute(null);
    }

    public void clickGlobalClicker() {
        globalClickLiveData.execute(null);
    }
}
