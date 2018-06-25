package com.evia.dagger2sampleapplication.clicker.acitivity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.UiHelper;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory factory;

    private ActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this, factory).get(ActivityViewModel.class);

        TextView globalCounterView = findViewById(R.id.global_counter);
        globalCounterView.setOnClickListener(v -> viewModel.clickGlobalClicker());
        viewModel.globalClickLiveDataMapped.observe(this, status -> handleCallState(globalCounterView, status));

        TextView activityCounterView = findViewById(R.id.activity_counter);
        activityCounterView.setOnClickListener(v -> viewModel.clickActivityClicker());
        viewModel.activityClickLiveDataMapped.observe(this, status -> handleCallState(activityCounterView, status));
    }

    private void handleCallState(TextView counterView, CallStatus<String, Void> status) {
        counterView.setText(status.getCurrentState());
        if (status instanceof CallStatus.Loading) {
            UiHelper.startLoading(counterView);
        } else {
            UiHelper.stopLoading(counterView);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
