package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.arch.lifecycle.Observer;

import com.evia.dagger2sampleapplication.R;
import com.evia.dagger2sampleapplication.common.presentationmodel.GenericModelSupport;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelAutoBinder;
import com.evia.dagger2sampleapplication.common.presentationmodel.ModelBinder;
import com.evia.dagger2sampleapplication.common.usecase.CallStatus;
import com.evia.dagger2sampleapplication.common.userinput.PresentationModelWithUserInput;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Evgenii Iashin on 18.07.18.
 */
public class UserInputPresentationModel extends PresentationModelWithUserInput<UserInputViewModel, ClickUserInput> {

    @Inject
    public UserInputPresentationModel(@Named("retainedModel") UserInputViewModel viewModel, GenericModelSupport genericModelSupport, ModelBinder.BindingOnPropertyChangedListener onPropertyChangedListener) {
        super(viewModel, genericModelSupport, onPropertyChangedListener);
    }

    public Observer<CallStatus<String, Void>> observer = status -> {
        this.status = status;
        notifyDataChanged(PROP_USER_INPUT_PROGRESS);
        notifyDataChanged(PROP_USER_INPUT_RESULT);
    };

    //user input handling

    @Override
    protected void onUserInputUpdated() {
        notifyDataChanged(PROP_USER_INPUT_SEARCH);
        //we can start initial loading here if needed
    }

    private static final String PROP_USER_INPUT_PROGRESS = "PROP_USER_INPUT_PROGRESS";
    private static final String PROP_USER_INPUT_RESULT = "PROP_USER_INPUT_RESULT";
    private static final String PROP_USER_INPUT_SEARCH = "PROP_USER_INPUT_SEARCH";

    //transient since it's gonna be updated after each orientation change
    private transient CallStatus<String, Void> status;

    @ModelAutoBinder.BindTextView(id = R.id.search_query, property = PROP_USER_INPUT_SEARCH)
    public CharSequence getSearchQuery() {
        return userInput.getClicksInput();
    }

    @ModelAutoBinder.BindTextView(id = R.id.search_query, property = PROP_USER_INPUT_SEARCH)
    public void setSearchQuery(String input) {
        userInput.setClicksInput(input);
    }

    @ModelAutoBinder.BindTextView(id = R.id.search_result, property = PROP_USER_INPUT_RESULT)
    public String getSearchResult() {
        return status.getCurrentState();
    }

    @ModelAutoBinder.BindOnClick(id = R.id.search_button)
    public void onFragmentCounterClick() {
        viewModel.execute(userInput.getClicksInput());
    }

    @ModelAutoBinder.BindProgress(id = R.id.search_result, property = PROP_USER_INPUT_PROGRESS)
    public boolean isProgressVisible() {
        return status instanceof CallStatus.Loading;
    }
}
