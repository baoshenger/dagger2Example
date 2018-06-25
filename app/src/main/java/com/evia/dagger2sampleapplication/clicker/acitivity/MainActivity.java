package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.clicker.ClickObserver;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
