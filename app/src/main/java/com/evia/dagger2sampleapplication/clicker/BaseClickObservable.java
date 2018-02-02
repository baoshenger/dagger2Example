package com.evia.dagger2sampleapplication.clicker;

import android.support.annotation.Nullable;

import com.evia.dagger2sampleapplication.ClickStorage;
import com.evia.dagger2sampleapplication.Logger;

import java.util.HashSet;

/**
 *  Base class with common logic for {@link ClickCounterObservable} specific implementations
 *
 * Created by Evgenii Iashin on 25.01.18.
 */
public class BaseClickObservable implements ClickCounterObservable {

    private final HashSet<ClickObserver> observers = new HashSet<>();

    private final String counterName;
    private int clickCount;
    @Nullable
    private ClickStorage clickStorage;
    private Logger logger;

    public BaseClickObservable(String counterName, @Nullable ClickStorage clickStorage, Logger logger) {
        this.counterName = counterName;
        this.clickStorage = clickStorage;
        this.logger = logger;

        if (clickStorage != null) {
            clickCount = clickStorage.getClicks();
        }
        logger.log(String.format("[%s] init", counterName));
    }

    @Override
    public void countClick() {
        clickCount++;
        if (clickStorage != null) {
            clickStorage.storeClicks(clickCount);
        }
        logger.log(String.format("[%s] %s", counterName, clickCount));
        notifyObservers();
    }

    @Override
    public void addObserver(ClickObserver observer) {
        observers.add(observer);
        observer.onClick(getState());
    }

    private String getState() {
        return String.format("%s state : %s clicks", counterName, clickCount);
    }

    @Override
    public void removeObserver(ClickObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        String state = getState();
        for (ClickObserver observer : observers) {
            observer.onClick(state);
        }
    }
}
