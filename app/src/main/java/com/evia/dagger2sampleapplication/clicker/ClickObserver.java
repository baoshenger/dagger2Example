package com.evia.dagger2sampleapplication.clicker;

/**
 *  Observer for user clicks state
 *
 * Created by Evgenii Iashin on 25.01.18.
 */
public interface ClickObserver {

    void onClick(String state);
}
