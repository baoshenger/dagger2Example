package com.evia.dagger2sampleapplication.common.userinput.storage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Evgenii Iashin on 18.07.18.
 */
public interface BundleStorage<UserInput> {

    /**
     *  Stores user input in provided bundle
     *
     * @param userInput - data to be stored
     * @param bundle - output bundle to hold stored data
     */
    void toBundle(UserInput userInput, Bundle bundle);

    /**
     *  Restores data from bundle if possible
     *
     * @param bundle - bundle which contains data
     * @return {@link UserInput} build from data stored in the bundle
     */
    @Nullable
    UserInput fromBundle(@NonNull Bundle bundle);

    /**
     *  Returns default data which is used if data can't be restored or doesn't exist yet
     *
     * @return default data
     */
    @NonNull
    UserInput getDefault();
}
