package com.evia.dagger2sampleapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.clicker.ClickObserver;
import com.evia.dagger2sampleapplication.scope.ActivityScope;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasFragmentInjector{

    private TextView globalCounterView;
    private TextView activityCounterView;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    @Named("global")
    BaseClickObservable globalClickCounter; //is accessible on application level

    @Inject
    @Named("activity")
    BaseClickObservable activityClickCounter; //lives only while we're in the Activity Scope

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalCounterView = findViewById(R.id.global_counter);
        globalCounterView.setOnClickListener(v -> globalClickCounter.countClick());

        activityCounterView = findViewById(R.id.activity_counter);
        activityCounterView.setOnClickListener(v -> activityClickCounter.countClick());
    }

    @Override
    protected void onStart() {
        super.onStart();

        globalClickCounter.addObserver(globalClickObserver);
        activityClickCounter.addObserver(activityClickObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        globalClickCounter.removeObserver(globalClickObserver);
        activityClickCounter.removeObserver(activityClickObserver);
    }

    private ClickObserver globalClickObserver = state -> globalCounterView.setText(state);

    private ClickObserver activityClickObserver = state -> activityCounterView.setText(state);

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @ActivityScope
    public static class ActivityClickCounter extends BaseClickObservable {

        private final BaseClickObservable globalClickCounter;

        @Inject
        public ActivityClickCounter(ActivityClickStorage activityClickStorage, @Named("global") BaseClickObservable globalClickCounter, Logger logger) {
            super("Activity", activityClickStorage, logger);
            this.globalClickCounter = globalClickCounter;
        }

        @Override
        public void countClick() {
            super.countClick();
            globalClickCounter.countClick();
        }
    }

    @ActivityScope
    public static class ActivityClickStorage implements ClickStorage {

        private static final String COUNT_EXTRA = "COUNT";

        private final Activity activity;

        @Inject
        public ActivityClickStorage(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void storeClicks(int clicks) {
            activity.getIntent().putExtra(COUNT_EXTRA, clicks);
        }

        @Override
        public int getClicks() {
            return activity.getIntent() != null ? activity.getIntent().getIntExtra(COUNT_EXTRA, 0) : 0;
        }
    }
}
