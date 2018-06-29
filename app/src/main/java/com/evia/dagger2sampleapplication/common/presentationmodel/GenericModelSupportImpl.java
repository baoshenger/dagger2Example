package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;

import javax.inject.Inject;

/**
 * Implementation of the generic model support.
 *
 * Created by bjoernquentin on 18.09.15.
 */
public class GenericModelSupportImpl implements GenericModelSupport {

    private final Context appCtx;

    @Inject
    public GenericModelSupportImpl(Application appCtx) {
        this.appCtx = appCtx;
    }

    @Override
    public String getString(@StringRes int resId) {
        return appCtx.getString(resId);
    }

    @Override
    public String getString(@StringRes int resId, Object... formatArgs) {
        return appCtx.getString(resId, formatArgs);
    }

    @Override
    public boolean isEmpty(@Nullable CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    @Override
    public int getInteger(@IntegerRes int resId) {
        return appCtx.getResources().getInteger(resId);
    }

    @Override
    public int getDimensionPixelSize(@DimenRes int resId) {
        return appCtx.getResources().getDimensionPixelSize(resId);
    }

    @Override
    public String getPluralsString(@PluralsRes int resId, int quantity, Object... formatArgs) {
        return appCtx.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    @Override
    public int getColor(@ColorRes int resId) {
        return ResourcesCompat.getColor(appCtx.getResources(), resId, null);
    }

    @Override
    public boolean isUiTestingMode() {
        return false;
    }

    @Override
    public Drawable getDrawable(int resId) {
        return appCtx.getResources().getDrawable(resId);
    }
}
