package com.evia.dagger2sampleapplication.clicker.fragment;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.presentationmodel.BasicFragmentWithPresentationModel;

/**
 * Created by Evgenii Iashin on 25.01.18.
 */
public class MainFragment extends BasicFragmentWithPresentationModel<MainFragmentViewModel, MainFragmentPresentationModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void bindPresentationModelToViewModel() {
        viewModel.fragmentClickLiveDataMapped.observe(this, presentationModel.observer);
    }

}
