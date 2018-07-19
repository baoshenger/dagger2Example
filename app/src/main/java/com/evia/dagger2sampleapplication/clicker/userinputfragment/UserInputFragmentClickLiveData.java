package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.util.Log;

import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.usecase.BaseUseCaseLiveData;

import javax.inject.Inject;

/**
 * Created by Evgenii Iashin on 25.06.18.
 */
@FragmentScope
public class UserInputFragmentClickLiveData extends BaseUseCaseLiveData<String, String, Void> {

    @Inject
    public UserInputFragmentClickLiveData(UserInputClickerUseCase clickerUseCase) {
        super(clickerUseCase, "default data");
        Log.i(getClass().getSimpleName(), "instance created");
    }
}
