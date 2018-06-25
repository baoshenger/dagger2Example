package com.evia.dagger2sampleapplication.clicker;

import android.support.annotation.WorkerThread;

/**
 *  User clicks counter interface
 *
 * Created by Evgenii Iashin on 25.01.18.
 */
public interface ClickCounter {

    @WorkerThread
    int countClick();
}
