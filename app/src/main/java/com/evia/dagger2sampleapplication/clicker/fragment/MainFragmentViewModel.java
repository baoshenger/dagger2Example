package com.evia.dagger2sampleapplication.clicker.fragment;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *  ViewModel for main fragment
 *
 * Created by Evgenii Iashin on 22.06.18.
 */
@FragmentScope
public class MainFragmentViewModel extends ViewModel {

    private final FragmentClickLiveData fragmentClickLiveData;
    public final LiveData<CallStatus<String, Void>> fragmentClickLiveDataMapped;

    @Inject
    public MainFragmentViewModel(FragmentClickLiveData fragmentClickLiveData, @Named("fragment") ClickerResultMapper mapper) {
        this.fragmentClickLiveData = fragmentClickLiveData;
        fragmentClickLiveDataMapped = Transformations.map(fragmentClickLiveData, (Function<CallStatus<Integer, Void>, CallStatus<String, Void>>) mapper::map);
        Log.i(getClass().getSimpleName(), "instance created");
    }

    public void click() {
        fragmentClickLiveData.execute(null);
    }

}
