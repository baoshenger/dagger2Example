package com.evia.dagger2sampleapplication.clicker.userinputfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.evia.dagger2sampleapplication.common.userinput.storage.BundleStorage;

import javax.inject.Inject;

/**
 * Created by Evgenii Iashin on 18.07.18.
 */
public class UserInputStorage implements BundleStorage<ClickUserInput> {

    private static final String INPUT_KEY = "some";

    @Inject
    public UserInputStorage() {
    }

    @Override
    public void toBundle(ClickUserInput clickUserInput, Bundle bundle) {
        bundle.putString(INPUT_KEY, clickUserInput.getClicksInput());
    }

    @Override
    public ClickUserInput fromBundle(@NonNull Bundle bundle) {
        final String clicksInput = bundle.getString(INPUT_KEY, "");
        return new ClickUserInput(clicksInput);
    }

    @NonNull
    @Override
    public ClickUserInput getDefault() {
        return new ClickUserInput("default from storage");
    }
}
