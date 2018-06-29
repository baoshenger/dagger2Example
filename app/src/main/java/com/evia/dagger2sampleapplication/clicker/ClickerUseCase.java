package com.evia.dagger2sampleapplication.clicker;

import android.util.Log;

import com.evia.dagger2sampleapplication.common.usecase.BaseUseCase;
import com.evia.dagger2sampleapplication.common.usecase.ResultOrFailure;

/**
 *  Use case for click counters
 *
 * Created by Evgenii Iashin on 22.06.18.
 */
public class ClickerUseCase extends BaseUseCase<Void, Integer, Void> {

    private final BaseClickCounter baseClickCounterObservable;

    public ClickerUseCase(BaseClickCounter baseClickCounterObservable) {
        this.baseClickCounterObservable = baseClickCounterObservable;
        Log.i(getClass().getSimpleName(), "instance created");
    }

    @Override
    protected ResultOrFailure<Integer, Void> job(Void aVoid) {
        return new ResultOrFailure<>(baseClickCounterObservable.countClick(), null);
    }
}
