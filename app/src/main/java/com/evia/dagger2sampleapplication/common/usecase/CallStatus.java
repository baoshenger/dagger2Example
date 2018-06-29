package com.evia.dagger2sampleapplication.common.usecase;

/**
 *  Data model class that represents current state of call and holds needed data
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
public class CallStatus<Result, Failure> {

    private final Result currentState;

    public CallStatus(Result currentState) {
        this.currentState = currentState;
    }

    public Result getCurrentState() {
        return currentState;
    }

    public static class Loading<Result, Failure> extends CallStatus<Result, Failure> {

        public Loading(Result currentState) {
            super(currentState);
        }
    }

    public static class Loaded<Result, Failure> extends CallStatus<Result, Failure> {

        public Loaded(Result currentState) {
            super(currentState);
        }
    }

    public static class Failed<Result, Failure> extends CallStatus<Result, Failure> {

        private final Failure reason;

        public Failed(Result currentState, Failure reason) {
            super(currentState);
            this.reason = reason;
        }

        public Failure getReason() {
            return reason;
        }
    }
}
