package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

/**
 * Interface for presentation models to abstract commonly needed things from the Android context.
 *
 * Created by bjoernquentin on 18.09.15.
 */
public interface GenericModelSupport {

    String getString(@StringRes int resId);

    String getString(@StringRes int resId, Object... formatArgs);

    boolean isEmpty(@Nullable CharSequence str);

    int getInteger(@IntegerRes int resId);

    int getDimensionPixelSize(@DimenRes int resId);

    String getPluralsString(@PluralsRes int resId, int quantity, Object... formatArgs);

    int getColor(@ColorRes int resId);

    boolean isUiTestingMode();

    Drawable getDrawable(int resId);
}
