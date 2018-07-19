package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.arch.lifecycle.ViewModel;

import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by Evgenii Iashin on 18.07.18.
 */
@FragmentScope
public class UserInputViewModel extends ViewModel {

    public final UserInputFragmentClickLiveData liveData;

    @Inject
    public UserInputViewModel(UserInputFragmentClickLiveData liveData) {
        this.liveData = liveData;
    }

    public void execute(String searchQuery) {
        liveData.execute(searchQuery);
    }

}
