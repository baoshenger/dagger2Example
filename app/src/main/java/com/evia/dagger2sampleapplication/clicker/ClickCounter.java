package com.evia.dagger2sampleapplication.clicker;

import android.support.annotation.WorkerThread;

/**
 *  Observable for user clicks
 *
 * Created by Evgenii Iashin on 25.01.18.
 */
public interface ClickCounter {

    @WorkerThread
    int countClick();
}
