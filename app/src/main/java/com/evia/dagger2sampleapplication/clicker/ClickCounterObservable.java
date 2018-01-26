package com.evia.dagger2sampleapplication.clicker;

/**
 * Created by Evgenii Iashin on 25.01.18.
 */

public interface ClickCounterObservable {

    void countClick();

    void addObserver(ClickObserver observer);
    void removeObserver(ClickObserver observer);

}
