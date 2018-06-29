package com.evia.dagger2sampleapplication.common.usecase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.Objects;

/**
 *  Base Live Data which wraps up base use cases
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
public class BaseUseCaseLiveData<Params, Result, Failure> extends MutableLiveData<CallStatus<Result, Failure>> {

    private final BaseUseCase<Params, Result, Failure> useCase;

    public BaseUseCaseLiveData(BaseUseCase<Params, Result, Failure> useCase, @NonNull Result defaultValue) {
        this.useCase = useCase;
        //need to initialize it first!!!
        setValue(new CallStatus.Loaded<>(defaultValue));
    }

    public void execute(Params params) {
        useCase.execute(params, new BaseUseCase.Callbacks<Result, Failure>() {
            @Override
            public void onLoading() {
                setValue(new CallStatus.Loading<>(getCurrentResult()));
            }

            @Override
            public void onResult(Result result) {
                setValue(new CallStatus.Loaded<>(result));
            }

            @Override
            public void onFailure(Failure failure) {
                setValue(new CallStatus.Failed<>(getCurrentResult(), failure));
            }
        });
    }

    private Result getCurrentResult() {
        //it's a wrapper, so we can control nullability
        return Objects.requireNonNull(getValue()).getCurrentState();
    }

}
