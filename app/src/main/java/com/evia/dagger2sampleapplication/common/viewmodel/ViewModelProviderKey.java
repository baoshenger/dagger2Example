package com.evia.dagger2sampleapplication.common.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 *  Just a key for ViewModel providers to be put into the {@link ViewModelFactory}
 *
 * Created by Evgenii Iashin on 22.06.18.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelProviderKey {
    Class<? extends ViewModel> value();
}
