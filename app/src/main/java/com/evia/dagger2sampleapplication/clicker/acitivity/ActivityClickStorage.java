package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.app.Activity;

import com.evia.dagger2sampleapplication.clicker.ClickStorage;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 *  Stores clicks in the activity intent
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@ActivityScope
public class ActivityClickStorage implements ClickStorage {

    private static final String COUNT_EXTRA = "COUNT";

    private final Activity activity;

    @Inject
    public ActivityClickStorage(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void storeClicks(int clicks) {
        activity.getIntent().putExtra(COUNT_EXTRA, clicks);
    }

    @Override
    public int getClicks() {
        return activity.getIntent() != null ? activity.getIntent().getIntExtra(COUNT_EXTRA, 0) : 0;
    }
}
