package com.evia.dagger2sampleapplication;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Evgenii Iashin on 05.02.18.
 */
public final class MockSharedPreferences implements SharedPreferences {

    @Inject
    public MockSharedPreferences() {
    }

    private Map<String, Object> holder = new HashMap<>();

    @Override
    public Map<String, ?> getAll() {
        return holder;
    }

    @Nullable
    @Override
    public String getString(String s, @Nullable String s1) {
        Object obj = holder.get(s);
        return obj != null ? (String) obj : s1;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        Object obj = holder.get(s);
        return obj != null ? (Set) obj : set;
    }

    @Override
    public int getInt(String s, int i) {
        Object obj = holder.get(s);
        return obj != null ? (int) obj : i;
    }

    @Override
    public long getLong(String s, long l) {
        Object obj = holder.get(s);
        return obj != null ? (long) obj : l;
    }

    @Override
    public float getFloat(String s, float v) {
        Object obj = holder.get(s);
        return obj != null ? (float) obj : v;
    }

    @Override
    public boolean getBoolean(String s, boolean b) {
        Object obj = holder.get(s);
        return obj != null ? (boolean) obj : b;
    }

    @Override
    public boolean contains(String s) {
        return holder.containsKey(s);
    }

    @Override
    public Editor edit() {
        return new TestEditor(holder);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    private static final class TestEditor implements Editor {

        private Map<String, Object> holder = new HashMap<>();
        private Map<String, Object> originHolder;

        public TestEditor(Map<String, Object> originHolder) {
            this.originHolder = originHolder;
            this.holder.putAll(originHolder);
        }

        @Override
        public Editor putString(String s, @Nullable String s1) {
            holder.put(s, s1);
            return this;
        }

        @Override
        public Editor putStringSet(String s, @Nullable Set<String> set) {
            holder.put(s, set);
            return this;
        }

        @Override
        public Editor putInt(String s, int i) {
            holder.put(s, i);
            return this;
        }

        @Override
        public Editor putLong(String s, long l) {
            holder.put(s, l);
            return this;
        }

        @Override
        public Editor putFloat(String s, float v) {
            holder.put(s, v);
            return this;
        }

        @Override
        public Editor putBoolean(String s, boolean b) {
            holder.put(s, b);
            return this;
        }

        @Override
        public Editor remove(String s) {
            holder.remove(s);
            return this;
        }

        @Override
        public Editor clear() {
            holder.clear();
            return this;
        }

        @Override
        public boolean commit() {
            originHolder.clear();
            originHolder.putAll(holder);
            return true;
        }

        @Override
        public void apply() {
            originHolder.clear();
            originHolder.putAll(holder);
        }
    }
}
