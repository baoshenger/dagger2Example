package com.evia.dagger2sampleapplication.clicker;

import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

/**
 *  Example of mapper
 *  NOTE: created just to illustrate the possibility, can be done in much smarter way
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
public class ClickerResultMapper<OriginResult, OriginFailure> {

    private final String tag;

    public ClickerResultMapper(String tag) {
        this.tag = tag;
    }

    public CallStatus<String, Void> map(CallStatus<OriginResult, OriginFailure> count) {
        if (count instanceof CallStatus.Failed) {
            CallStatus.Failed<OriginResult, OriginFailure> failed = (CallStatus.Failed<OriginResult, OriginFailure>) count;
            return new CallStatus.Failed<>(mapResult(count.getCurrentState()), mapFailure(failed.getReason()));
        } else if (count instanceof CallStatus.Loading) {
            return new CallStatus.Loading<>(mapResult(count.getCurrentState()));
        } else {
            return new CallStatus.Loaded<>(mapResult(count.getCurrentState()));
        }
    }

    private String mapResult(OriginResult result) {
        return String.format("%s state : %s clicks", tag, result);
    }

    private Void mapFailure(OriginFailure failure) {
        return null;
    }
}
