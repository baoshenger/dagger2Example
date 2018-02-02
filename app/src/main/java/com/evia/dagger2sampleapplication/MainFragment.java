package com.evia.dagger2sampleapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.clicker.ClickObserver;
import com.evia.dagger2sampleapplication.scope.FragmentScope;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;

/**
 * Created by Evgenii Iashin on 25.01.18.
 */

public class MainFragment extends Fragment {

    @Inject
    @Named("fragment")
    BaseClickObservable fragmentClickCounter;

    private TextView fragmentCounterView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentClickCounter.removeObserver(clickObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentClickCounter.addObserver(clickObserver);
    }

    private ClickObserver clickObserver = state -> fragmentCounterView.setText(state);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container);

        fragmentCounterView = root.findViewById(R.id.fragment_counter);
        fragmentCounterView.setOnClickListener(v -> fragmentClickCounter.countClick());

        return root;
    }

    @FragmentScope
    public static class FragmentClickCounter extends BaseClickObservable {

        private final BaseClickObservable activityClickCounter;

        @Inject
        public FragmentClickCounter(@Named("activity") BaseClickObservable activityClickCounter, Logger logger) {
            super("Fragment", null, logger);
            this.activityClickCounter = activityClickCounter;
        }

        @Override
        public void countClick() {
            super.countClick();
            activityClickCounter.countClick();
        }
    }
}
