package com.evia.dagger2sampleapplication.common.usecase;

import android.support.annotation.Nullable;

/**
 *  Holder for either Result or Failure
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
public class ResultOrFailure<Result, Failure> {

    private Result result;
    private Failure failure;

    public ResultOrFailure(@Nullable Result result, @Nullable Failure failure) {
        if (failure != null) {
            this.failure = failure;
        } else {
            this.result = result;
        }
    }

    public boolean isFailure() {
        return failure != null;
    }

    public Result getResult() {
        return result;
    }

    public Failure getFailure() {
        return failure;
    }
}
