package com.evia.dagger2sampleapplication.clicker.fragment;

import android.util.Log;

import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.clicker.acitivity.ActivityClickLiveData;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.usecase.BaseUseCaseLiveData;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *  Live data for {@link FragmentClickCounter}
 *  NOTE: will update {@link ActivityClickLiveData}
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@FragmentScope
public class FragmentClickLiveData extends BaseUseCaseLiveData<Void, Integer, Void> {

    private final ActivityClickLiveData activityClickLiveData;

    @Inject
    public FragmentClickLiveData(@Named("fragment") ClickerUseCase clickerUseCase, ActivityClickLiveData activityClickLiveData) {
        super(clickerUseCase, 0);
        this.activityClickLiveData = activityClickLiveData;
        Log.i(getClass().getSimpleName(), "instance created");
    }

    @Override
    public void execute(Void aVoid) {
        activityClickLiveData.execute(aVoid);
        super.execute(aVoid);
    }
}
