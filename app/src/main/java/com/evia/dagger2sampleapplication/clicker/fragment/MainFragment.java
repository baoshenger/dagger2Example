package com.evia.dagger2sampleapplication.clicker.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.R;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Evgenii Iashin on 25.01.18.
 */
public class MainFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory factory;

    private MainFragmentViewModel mainFragmentViewModel;

    private TextView fragmentCounterView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        this.mainFragmentViewModel = ViewModelProviders.of(this, factory).get(MainFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container);

        fragmentCounterView = root.findViewById(R.id.fragment_counter);
        fragmentCounterView.setOnClickListener(v -> mainFragmentViewModel.click());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentViewModel.clickerText.observe(this, fragmentCounterView::setText);
    }

}
