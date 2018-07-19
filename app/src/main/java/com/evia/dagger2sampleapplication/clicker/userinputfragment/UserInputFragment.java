package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.userinput.storage.BasicFragmentWithUserInputPersistenceInBundle;

/**
 * Created by Evgenii Iashin on 17.07.18.
 */
public class UserInputFragment extends BasicFragmentWithUserInputPersistenceInBundle<ClickUserInput, UserInputViewModel, UserInputPresentationModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_userinput;
    }

    @Override
    protected void bindPresentationModelToViewModel() {
        viewModel.liveData.observe(this, presentationModel.observer);
    }

}
