package com.evia.dagger2sampleapplication.clicker.acitivity;

import com.evia.dagger2sampleapplication.common.Logger;
import com.evia.dagger2sampleapplication.clicker.BaseClickCounter;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 *  Click counter accessible on the activity level
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@ActivityScope
public class ActivityClickCounter extends BaseClickCounter {

    @Inject
    public ActivityClickCounter(ActivityClickStorage activityClickStorage, Logger logger) {
        super("Activity", activityClickStorage, logger);
    }
}
