package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.support.annotation.NonNull;

/**
 * @author robertgeldmacher
 */
public interface OnPropertyChangedListener {

    void onPropertyChanged(@NonNull String propertyName);
}
