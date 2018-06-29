package com.evia.dagger2sampleapplication.common;

import android.graphics.Color;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 *  Helper class for UI related stuff
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
public class UiHelper {

    public static void startLoading(TextView view) {
        view.setTextColor(Color.RED);
        Animation anim = new AlphaAnimation(0.7f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
        view.setEnabled(false);
        final Handler handler = view.getHandler();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public static void stopLoading(TextView view) {
        view.setEnabled(true);
        view.setTextColor(Color.GREEN);
        view.clearAnimation();
        view.postDelayed(() -> view.setTextColor(Color.BLACK), 300);
    }
}
