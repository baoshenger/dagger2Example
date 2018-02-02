package com.evia.dagger2sampleapplication;

/**
 *  Clicks storage
 *
 * Created by Evgenii Iashin on 02.02.18.
 */
public interface ClickStorage {

    void storeClicks(int clicks);
    int getClicks();
}
