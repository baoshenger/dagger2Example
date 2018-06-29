package com.evia.dagger2sampleapplication.common.presentationmodel;


import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.evia.dagger2sampleapplication.common.UiHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.inject.Inject;

/**
 * Some simple helpers for binding models and views.
 *
 * See documentation:
 * https://confluence.int.hrs.com/confluence/pages/viewpage.action?pageId=17730441
 * <p/>
 * Created by bjoernquentin on 16.09.15.
 */
public final class ModelBinder {

    public static final int NO_INDEX = -1;

    public static final int DRAWABLE_LEFT_INDEX = 0;
    public static final int DRAWABLE_TOP_INDEX = 1;
    public static final int DRAWABLE_RIGHT_INDEX = 2;
    public static final int DRAWABLE_BOTTOM_INDEX = 3;

    private static final int CROSSFADE_ANIMATION_DURATION = 500;
    private static final float BLUR_DOWN_SAMPLING = 0.02f;

    /**
     * Sets a model's value.
     *
     * @param <T> the type
     */
    public interface ModelSetter<T> {

        void setValue(T value);
    }

    /**
     * Gets a model's value.
     *
     * @param <T> the type
     */
    public interface ModelGetter<T> {

        T getValue();
    }

    /**
     * Simple interface for things like clicks etc.
     */
    public interface ModelCallback {

        void call();
    }

    /**
     * Sets a model's value.
     *
     * @param <T> the type
     */
    public interface IndexedModelSetter<T> {

        void setValue(int index, T value);
    }

    /**
     * Gets a model's value.
     *
     * @param <T> the type
     */
    public interface IndexedModelGetter<T> {

        T getValue(int index);
    }

    /**
     * Simple interface for things like clicks etc.
     */
    public interface IndexedModelCallback {

        void call(int index);
    }

    private ModelBinder() {
    }

    public static BindingOnPropertyChangedListener bindTextViewToModel(TextView tv, String property, final ModelGetter<CharSequence> mg, BindingOnPropertyChangedListener propertyChangeListener) {
        return bindTextViewToModel(tv, property, NO_INDEX, new ModelGetterWrapper<>(mg), propertyChangeListener);
    }

    public static BindingOnPropertyChangedListener bindTextViewToModel(TextView tv, String property, final int index, final IndexedModelGetter<CharSequence> mg,
        BindingOnPropertyChangedListener propertyChangeListener) {
        propertyChangeListener.addPropertyChangeHandler(property, () -> {
            tv.setText(mg.getValue(index));
            }, tv);
        return propertyChangeListener;
    }

    public static BindingOnPropertyChangedListener bindProgressToModel(final TextView view, String property, final ModelGetter<Boolean> mg, BindingOnPropertyChangedListener propertyChangeListener) {
        return bindProgressToModel(view, property, NO_INDEX, new ModelGetterWrapper<>(mg), propertyChangeListener);
    }

    public static BindingOnPropertyChangedListener bindProgressToModel(final TextView view,
        String property, int index, final IndexedModelGetter<Boolean> mg, BindingOnPropertyChangedListener propertyChangeListener) {
        propertyChangeListener.addPropertyChangeHandler(property, () -> {
            final Boolean isInProgress = mg.getValue(index);
            if (isInProgress) {
                UiHelper.startLoading(view);
            } else {
                UiHelper.stopLoading(view);
            }
        }, view);
        return propertyChangeListener;
    }

    public static void bindModelToButton(View v, final String property, final ModelCallback mc, final BindingOnPropertyChangedListener propertyChangeListener) {
        bindModelToButton(v, property, NO_INDEX, new ModelCallbackWrapper(mc), propertyChangeListener);
    }

    public static void bindModelToButton(View v, final String property, int index, final IndexedModelCallback mc,
                                         final BindingOnPropertyChangedListener propertyChangeListener) {
        v.setOnClickListener(v1 -> {
            propertyChangeListener.setIsBinding(property, true);
            mc.call(index);
            propertyChangeListener.setIsBinding(property, false);
        });
    }

    public static void bindModelToButton(View v, final ModelCallback mc) {
        bindModelToButton(v, NO_INDEX, new ModelCallbackWrapper(mc));
    }

    public static void bindModelToButton(View v, int index, final IndexedModelCallback mc) {
        v.setOnClickListener(v1 -> mc.call(index));
    }

    public static void bindModelToEditText(EditText tv, final String property, final ModelSetter<String> ms, final BindingOnPropertyChangedListener propertyChangeListener) {
        bindModelToEditText(tv, property, NO_INDEX, new ModelSetterWrapper<>(ms), propertyChangeListener);
    }

    public static void bindModelToEditText(EditText tv, final String property, int index,
                                           final IndexedModelSetter<String> ms, final BindingOnPropertyChangedListener propertyChangeListener) {
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                propertyChangeListener.setIsBinding(property, true);
                ms.setValue(index, s.toString());
                propertyChangeListener.setIsBinding(property, false);
            }
        });
    }

    /**
     * Implementation of a property change listener which is directly used by the util methods.
     * Make sure to have it called by the model (directly or delegate to it).
     */
    public static class BindingOnPropertyChangedListener implements OnPropertyChangedListener {

        @Inject
        public BindingOnPropertyChangedListener() {
        }

        /**
         * Internally used by the binding utils.
         */
        interface PropertyChangeHandler {

            void syncToView();
        }

        /**
         * Internally used by the binding utils.
         */
        final static class PropertyChangeHandlerWrapper {

            private final WeakReference viewRef;
            private final PropertyChangeHandler handler;

            public PropertyChangeHandlerWrapper(View view, PropertyChangeHandler handler) {
                viewRef = new WeakReference<>(view);
                this.handler = handler;
            }

            public boolean syncToView() {
                if (viewRef.get() != null) {
                    handler.syncToView();
                    return true;
                }
                return false;
            }

        }

        private final HashMap<String, Boolean> isBinding = new HashMap<>();
        private final HashMap<String, ArrayList<PropertyChangeHandlerWrapper>> propertyChangeHandlers = new HashMap<>();
        private final HashSet<String> boundProperties = new HashSet<>();


        void setIsBinding(String propertyName, boolean isBinding) {
            this.isBinding.put(propertyName, isBinding);
        }

        void addPropertyChangeHandler(String property, PropertyChangeHandler handler, View view) {
            ArrayList<PropertyChangeHandlerWrapper> handlers = propertyChangeHandlers.get(property);
            if (handlers == null) {
                handlers = new ArrayList<>();
                propertyChangeHandlers.put(property, handlers);
            }

            ArrayList<PropertyChangeHandlerWrapper> toEvict = new ArrayList<>();
            for (PropertyChangeHandlerWrapper handlerToEvict : handlers) {
                if (handlerToEvict.viewRef.get() == view) {
                    toEvict.add(handlerToEvict);
                }
            }
            handlers.removeAll(toEvict);

            handlers.add(new PropertyChangeHandlerWrapper(view, handler));
            boundProperties.add(property);
        }

        void removePropertyChangeHandler(String property, View view) {
            ArrayList<PropertyChangeHandlerWrapper> handlers = propertyChangeHandlers.get(property);
            if (handlers == null) {
                return;
            }
            ArrayList<PropertyChangeHandlerWrapper> toEvict = new ArrayList<>();

            for (PropertyChangeHandlerWrapper handlerToEvict : handlers) {
                if (handlerToEvict.viewRef.get() == view) {
                    toEvict.add(handlerToEvict);
                }
            }
            handlers.removeAll(toEvict);
        }

        @Override
        public void onPropertyChanged(@NonNull String propertyName) {
            ArrayList<PropertyChangeHandlerWrapper> handlers = propertyChangeHandlers.get(propertyName);

            ArrayList<PropertyChangeHandlerWrapper> toEvict = new ArrayList<>();
            if (handlers != null && !isBinding(propertyName)) {
                for (PropertyChangeHandlerWrapper handler : handlers) {
                    if (!handler.syncToView()) {
                        toEvict.add(handler);
                    }
                }
            }
            if (handlers != null) {
                for (PropertyChangeHandlerWrapper propertyChangeHandlerWrapper : toEvict) {
                    handlers.remove(propertyChangeHandlerWrapper);
                }
            }
        }

        private boolean isBinding(String propertyName) {
            Boolean isBinding = this.isBinding.get(propertyName);
            return Boolean.TRUE.equals(isBinding);
        }

        public void syncAllViews() {
            String[] allCurrentlyBoundProperties = propertyChangeHandlers.keySet().toArray(new String[propertyChangeHandlers.size()]);
            for (String property : allCurrentlyBoundProperties) {
                if (!isBinding(property)) {
                    onPropertyChanged(property);
                }
            }
        }

        public void startFreshBinding() {
            boundProperties.clear();
        }

        public void syncFreshBound() {
            for (String boundProperty : boundProperties) {
                onPropertyChanged(boundProperty);
            }
        }

    }

    /**
     * Wraps a ModelSetter as an IndexedModelSetter
     *
     * @param <T> the type
     */
    public static class ModelSetterWrapper<T> implements IndexedModelSetter<T> {

        private final ModelSetter<T> setter;

        public ModelSetterWrapper(ModelSetter<T> setter) {
            this.setter = setter;
        }

        @Override
        public void setValue(int index, T value) {
            setter.setValue(value);
        }

    }

    /**
     * Wraps a ModelGetter as an IndexedModelGetter
     *
     * @param <T> the type
     */
    public static class ModelGetterWrapper<T> implements IndexedModelGetter<T> {

        private final ModelGetter<T> getter;

        public ModelGetterWrapper(ModelGetter<T> getter) {
            this.getter = getter;
        }

        @Override
        public T getValue(int index) {
            return getter.getValue();
        }

    }


    /**
     * Wraps a ModelCallback as an IndexedModelCallback
     */
    public static class ModelCallbackWrapper implements IndexedModelCallback {

        private final ModelCallback modelCallback;

        public ModelCallbackWrapper(ModelCallback modelCallback) {
            this.modelCallback = modelCallback;
        }

        @Override
        public void call(int index) {
            modelCallback.call();
        }
    }

}
