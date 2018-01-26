package com.evia.dagger2sampleapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.clicker.ClickObserver;

public class MainActivity extends AppCompatActivity {

    private ActivityClickCounter activityClickCounter;
    private TextView globalCounterView;
    private TextView activityCounterView;
    private SampleApplication.GlobalClickCounter globalClickCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalClickCounter = SampleApplication.GlobalClickCounter.getInstance(getApplication());
        activityClickCounter = new ActivityClickCounter(this);

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

    /**
     *  Bad way (let's imagine that we do it through interface)
     */
    public ActivityClickCounter getActivityClickCounter() {
        return activityClickCounter;
    }

    public static class ActivityClickCounter extends BaseClickObservable {

        private final Activity activity;

        public ActivityClickCounter(Activity activity) {
            super("Activity", activity.getIntent() != null ? activity.getIntent().getIntExtra("COUNT", 0) : 0);
            this.activity = activity;
        }

        @Override
        public void countClick() {
            super.countClick();
            activity.getIntent().putExtra("COUNT", clickCount);
            SampleApplication.GlobalClickCounter.getInstance(activity.getApplication()).countClick();
        }
    }
}
