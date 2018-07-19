package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.util.Log;

import com.evia.dagger2sampleapplication.common.usecase.BaseUseCase;
import com.evia.dagger2sampleapplication.common.usecase.ResultOrFailure;

import javax.inject.Inject;

/**
 *  Use case for click counters with params
 *
 * Created by Evgenii Iashin on 22.06.18.
 */
public class UserInputClickerUseCase extends BaseUseCase<String, String, Void> {

    private String searchQueryCache;

    @Inject
    public UserInputClickerUseCase() {
        Log.i(getClass().getSimpleName(), "instance created");
    }

    @Override
    protected ResultOrFailure<String, Void> job(String searchQuery) {
        if (searchQueryCache != null && searchQueryCache.equals(searchQuery)) {
            return new ResultOrFailure<>(String.format("Cached result for the click %s", searchQuery), null);
        }

        String result = "result";
        try {
            Thread.sleep(1000);
            result = String.format("Result for the click %s", searchQuery);
            searchQueryCache = searchQuery;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResultOrFailure<>(result, null);
    }
}
