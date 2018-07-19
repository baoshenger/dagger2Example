package com.evia.dagger2sampleapplication.clicker.acitivity;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.presentationmodel.BasicActivityWithPresentationModel;

public class MainActivity extends BasicActivityWithPresentationModel<ActivityViewModel, MainActivityPresentationModel> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindPresentationModelToViewModel() {
        viewModel.activityClickLiveDataMapped.observe(this, presentationModel.activityClickObserver);
        viewModel.globalClickLiveDataMapped.observe(this, presentationModel.globalClickObserver);
    }

}
