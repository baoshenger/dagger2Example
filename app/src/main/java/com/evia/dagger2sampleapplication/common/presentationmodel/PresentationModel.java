package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * VM base presentation model for shared basic functionality.
 * Created by bjoernquentin on 04.11.16.
 *
 * @param <VM> The type parameter for the action callbacks.
 */
public class PresentationModel<VM extends ViewModel> {

    private static final String TAG = PresentationModel.class.getSimpleName();

    protected VM viewModel;
    private OnPropertyChangedListener onPropertyChangedListener;
    protected GenericModelSupport genericModelSupport;

    public PresentationModel(VM viewModel, GenericModelSupport genericModelSupport, OnPropertyChangedListener onPropertyChangedListener) {
        this.viewModel = viewModel;
        this.genericModelSupport = genericModelSupport;
        this.onPropertyChangedListener = onPropertyChangedListener;
    }

    protected void notifyDataChanged(@NonNull final String property) {
        if (onPropertyChangedListener != null) {
            onPropertyChangedListener.onPropertyChanged(property);
        }
    }

    public void autoBind(View view) {
        if (view instanceof ViewGroup) {
            if (onPropertyChangedListener instanceof ModelBinder.BindingOnPropertyChangedListener) {
                ModelAutoBinder.autoBind((ModelBinder.BindingOnPropertyChangedListener) onPropertyChangedListener, (ViewGroup) view, this, true);
            } else {
                Log.e(TAG, "trying to auto bind presentation model with a property change listener which doesn't support auto binding");
            }
        } else {
            Log.e(TAG, "trying to auto bind presentation model to a view which isn't a view group");
        }
    }
}
