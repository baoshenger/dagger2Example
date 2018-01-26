package com.evia.dagger2sampleapplication.clicker;

import java.util.HashSet;

/**
 * Created by Evgenii Iashin on 25.01.18.
 */

public class BaseClickObservable implements ClickCounterObservable {

    private final HashSet<ClickObserver> observers = new HashSet<>();

    private final String counterName;
    protected int clickCount;

    public BaseClickObservable(String counterName, int initialCount) {
        this.counterName = counterName;
        clickCount = initialCount;
    }

    public BaseClickObservable(String counterName) {
        this(counterName, 0);
    }

    @Override
    public void countClick() {
        clickCount++;
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
