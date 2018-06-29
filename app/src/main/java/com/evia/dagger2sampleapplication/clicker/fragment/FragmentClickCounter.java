package com.evia.dagger2sampleapplication.clicker.fragment;

import android.util.Log;

import com.evia.dagger2sampleapplication.common.Logger;
import com.evia.dagger2sampleapplication.clicker.BaseClickCounter;
import com.evia.dagger2sampleapplication.common.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 *  Click counter accessible on the fragment level
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@FragmentScope
public class FragmentClickCounter extends BaseClickCounter {

    @Inject
    public FragmentClickCounter(Logger logger) {
        super("Fragment", null, logger);
        Log.i(getClass().getSimpleName(), "instance created");
    }
}
