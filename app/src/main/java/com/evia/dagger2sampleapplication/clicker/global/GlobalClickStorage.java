package com.evia.dagger2sampleapplication.clicker.global;

import android.content.SharedPreferences;

import com.evia.dagger2sampleapplication.clicker.ClickStorage;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *  Storage for global clicks
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@Singleton
public class GlobalClickStorage implements ClickStorage {

    private static final String COUNT_KEY = "COUNT_KEY";

    private final SharedPreferences sharedPreferences;

    @Inject
    public GlobalClickStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void storeClicks(int clicks) {
        sharedPreferences.edit().putInt(COUNT_KEY, clicks).apply();
    }

    @Override
    public int getClicks() {
        return sharedPreferences.getInt(COUNT_KEY, 0);
    }
}
