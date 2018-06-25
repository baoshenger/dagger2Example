package com.evia.dagger2sampleapplication.clicker;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.evia.dagger2sampleapplication.Logger;

/**
 *  Base class with common logic for {@link ClickCounter} specific implementations
 *
 * Created by Evgenii Iashin on 25.01.18.
 */
public class BaseClickCounter implements ClickCounter {

    private final String counterName;
    @VisibleForTesting
    public int clickCount;
    @Nullable
    private ClickStorage clickStorage;
    private Logger logger;

    public BaseClickCounter(String counterName, @Nullable ClickStorage clickStorage, Logger logger) {
        this.counterName = counterName;
        this.clickStorage = clickStorage;
        this.logger = logger;

        if (clickStorage != null) {
            clickCount = clickStorage.getClicks();
        }
        logger.log(String.format("[%s] init", counterName));
    }

    @Override
    public int countClick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickCount++;
        if (clickStorage != null) {
            clickStorage.storeClicks(clickCount);
        }
        logger.log(String.format("[%s] %s", counterName, clickCount));
        return clickCount;
    }
}
