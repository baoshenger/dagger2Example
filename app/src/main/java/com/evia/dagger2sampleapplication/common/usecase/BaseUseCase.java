package com.evia.dagger2sampleapplication.common.usecase;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

/**
 *  Base use case that does his job in the background thread based on AsyncTask for simplicity
 *
 * Created by Evgenii Iashin on 22.06.18.
 */
public abstract class BaseUseCase<Params, Result, Failure> {

    public BaseUseCase() {

    }

    @SuppressLint("StaticFieldLeak") //skip for simplicity
    @SuppressWarnings("unchecked") //skip since it's controlled via generics, so fine
    @MainThread
    protected void execute(Params params, BaseUseCase.Callbacks<Result, Failure> callbacks) {
        new JobAsyncTask<Params, Result, Failure>(callbacks) {

            @Override
            protected ResultOrFailure<Result, Failure> doInBackground(Params... params) {
                return job(params[0]);
            }
        }.execute(params);
    }

    @WorkerThread
    protected abstract ResultOrFailure<Result, Failure> job(Params params);

    /**
     *  Callbacks for background work
     *
     * @param <Result>
     * @param <Failure>
     */
    public interface Callbacks<Result, Failure> {
        void onLoading();
        void onResult(Result result);
        void onFailure(Failure failure);
    }

    /**
     *  Async task binded to {@link Callbacks} interface
     *
     * @param <Params>
     * @param <Result>
     * @param <Failure>
     */
    private abstract static class JobAsyncTask<Params, Result, Failure> extends AsyncTask<Params, Void, ResultOrFailure<Result, Failure>> {

        private final Callbacks<Result, Failure> callbacks;

        public JobAsyncTask(Callbacks<Result, Failure> callbacks) {
            this.callbacks = callbacks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callbacks.onLoading();
        }

        @Override
        protected void onPostExecute(ResultOrFailure<Result, Failure> result) {
            super.onPostExecute(result);
            if (result.isFailure()) {
                callbacks.onFailure(result.getFailure());
            } else {
                callbacks.onResult(result.getResult());
            }
        }
    }

}
