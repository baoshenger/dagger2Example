package com.evia.dagger2sampleapplication.common.presentationmodel;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Uses annotations for ModelBinder.
 * Not every bindable widget is supported yet. Add your own annotations as needed.
 * <p/>
 * Created by bjoern on 29.02.2016.
 */
public final class ModelAutoBinder {

    private ModelAutoBinder() {

    }

    private static final AnnotationHandler[] ANNOTATION_HANDLERS = new AnnotationHandler[]{
            new BindTextViewAnnotationHandler(),
            new BindOnClickAnnotationHandler(),
            new BindProgressAnnotationHandler()
    };


    /**
     * Annotation for auto binding.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindTextView {

        int id() default 0;

        String idName() default "";

        String property();

        //indicates that annotation is optional and it's allowed that there is no view related to it at some moment
        //NOTE: possible use-case: view is available only on some screen configurations
        boolean isOptional() default false;
    }

    /**
     * Annotation for auto binding.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindOnClick {

        int id() default 0;

        String idName() default "";

        //indicates that annotation is optional and it's allowed that there is no view related to it at some moment
        //NOTE: possible use-case: view is available only on some screen configurations
        boolean isOptional() default false;
    }

    /**
     * Annotation for binding the progress state of a text view
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindProgress {

        @IdRes
        int id();

        String property();

        //indicates that annotation is optional and it's allowed that there is no view related to it at some moment
        //NOTE: possible use-case: view is available only on some screen configurations
        boolean isOptional() default false;
    }

    /**
     * To be implemented by AnnotationHandler for auto binding.
     *
     * @param <T> view type
     */
    interface AnnotationHandler<T extends View> {

        Class<? extends Annotation> getAnnotationClass();

        void handleSetter(Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, T view, Object model, String propertyName, int index);

        void handleGetter(Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, T view, Object model, String propertyName, int index);

        int getIdFromAnnotation(Context context, Annotation annotation);

        String getPropertyFromAnnotation(Annotation annotation);

        boolean isOptionalFromAnnotation(Annotation annotation);

        boolean isCallbackHandler();
    }

    public static ModelBinder.BindingOnPropertyChangedListener autoBind(final ViewGroup rootView, final Object model) {
        ModelBinder.BindingOnPropertyChangedListener changeListener = new ModelBinder.BindingOnPropertyChangedListener();
        autoBind(changeListener, rootView, model);
        return changeListener;
    }

    public static void autoBind(final ModelBinder.BindingOnPropertyChangedListener changedListener, final ViewGroup rootView, final Object model) {
        autoBind(changedListener, rootView, model, false);
    }

    @SuppressWarnings("unchecked")
    public static void autoBind(final ModelBinder.BindingOnPropertyChangedListener changedListener, final ViewGroup rootView, final Object model, final boolean silent) {
        autoBind(changedListener, rootView, model, ModelBinder.NO_INDEX, silent);
    }

    @SuppressWarnings("unchecked")
    public static void autoBind(final ModelBinder.BindingOnPropertyChangedListener changedListener, final ViewGroup rootView, final Object model, int index, final boolean silent) {
        Class clazz = model.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {

            for (AnnotationHandler handler : ANNOTATION_HANDLERS) {

                Class<? extends Annotation> annotationToHandle = handler.getAnnotationClass();
                Annotation annotation = method.getAnnotation(annotationToHandle);

                if (annotation == null) {
                    continue;
                }

                // a setter has parameters and no return type
                final boolean isSetter = !handler.isCallbackHandler() && (method.getReturnType().equals(Void.TYPE) && method.getParameterTypes() != null && method.getParameterTypes().length > 0);

                final View view = rootView.findViewById(handler.getIdFromAnnotation(rootView.getContext(), annotation));
                final String propertyName = handler.getPropertyFromAnnotation(annotation);

                //TODO think of a way to eliminate blind spots like views inside of a collection or parameterised methods
                //view inside of a collection is a special case since we iterate through a full list of annotations related to a model, but searching for a subset of it
                boolean isNotInsideCollection = index == ModelBinder.NO_INDEX;
                //parameterised method is a special case since a corresponding view can be unavailable yet on the time of binding
                boolean isNotParameterised = method.getParameterTypes().length == 0;
                boolean isUnderDevelopment = true;
                //optional means that it's explicitly accepted that the view for this annotation can be null
                boolean isNotOptional = !handler.isOptionalFromAnnotation(annotation);

                boolean isNotSpecialCase = isNotParameterised && isNotOptional && isNotInsideCollection;

                //force it to throw an exception for Debug and QA builds to be able to catch this kind of issues on an earlier stage of development
                if ((!silent || isUnderDevelopment) && view == null && isNotSpecialCase) {
                    throw new RuntimeException(String.format("No view found for property '%s' of annotation '%s'", handler.getPropertyFromAnnotation(annotation), annotation.toString()));
                }

                if (view != null) {
                    if (isSetter) {
                        handler.handleSetter(method, changedListener, view, model, propertyName, index);
                    } else {
                        handler.handleGetter(method, changedListener, view, model, propertyName, index);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void removeBind(final ModelBinder.BindingOnPropertyChangedListener changedListener, final ViewGroup rootView, final Object model, final boolean silent) {
        Class clazz = model.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {

            for (AnnotationHandler handler : ANNOTATION_HANDLERS) {

                Class<? extends Annotation> annotationToHandle = handler.getAnnotationClass();
                Annotation annotation = method.getAnnotation(annotationToHandle);

                if (annotation == null) {
                    continue;
                }

                final View view = rootView.findViewById(handler.getIdFromAnnotation(rootView.getContext(), annotation));
                final String propertyName = handler.getPropertyFromAnnotation(annotation);
                changedListener.removePropertyChangeHandler(propertyName, view);
            }
        }
    }


    /**
     * Convenience class - only used internally.
     *
     * @param <T> setter parameter type
     */
    private static class IndexedModelSetterImpl<T> implements ModelBinder.IndexedModelSetter<T> {

        private final Method method;
        private final Object model;

        public IndexedModelSetterImpl(Method method, Object model) {
            this.method = method;
            this.model = model;
        }

        @Override
        public void setValue(int index, T value) {
            try {
                if (index != ModelBinder.NO_INDEX) {
                    method.invoke(model, index, value);
                } else {
                    method.invoke(model, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("BindingException", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("BindingException", e);
            }
        }
    }


    /**
     * Convenience class - only used internally.
     *
     * @param <T> getter parameter type
     */
    private static class IndexedModelGetterImpl<T> implements ModelBinder.IndexedModelGetter<T> {

        private final Method method;
        private final Object model;

        public IndexedModelGetterImpl(Method method, Object model) {
            this.method = method;
            this.model = model;
        }

        @Override
        public T getValue(int index) {
            try {
                if (index != ModelBinder.NO_INDEX) {
                    return (T) method.invoke(model, index);
                } else {
                    return (T) method.invoke(model);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("BindingException", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("BindingException", e);
            }
        }
    }

    /**
     * Convenience class - only used internally.
     */
    private static class IndexedModelCallbackImpl implements ModelBinder.IndexedModelCallback {

        private final Method method;
        private final Object model;

        public IndexedModelCallbackImpl(Method method, Object model) {
            this.method = method;
            this.model = model;
        }

        @Override
        public void call(int index) {
            try {
                if (index == ModelBinder.NO_INDEX) {
                    method.invoke(model);
                } else {
                    method.invoke(model, index);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("BindingException", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("BindingException", e);
            }
        }
    }

    /**
     * AnnotationHandler for TextView Binding.
     */
    private static class BindTextViewAnnotationHandler implements AnnotationHandler<TextView> {

        @Override
        public int getIdFromAnnotation(Context context, Annotation annotation) {
            int id = ((BindTextView) annotation).id();
            if (id != 0) {
                return id;
            }
            return context.getResources().getIdentifier(((BindTextView) annotation).idName(), "id", context.getPackageName());

        }

        @Override
        public String getPropertyFromAnnotation(Annotation annotation) {
            return ((BindTextView) annotation).property();
        }

        @Override
        public boolean isCallbackHandler() {
            return false;
        }

        @Override
        public Class<? extends Annotation> getAnnotationClass() {
            return BindTextView.class;
        }

        @Override
        public void handleSetter(final Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, TextView view, final Object model, String propertyName, int boundIndex) {
            ModelBinder.bindModelToEditText((EditText) view, propertyName, boundIndex, new IndexedModelSetterImpl<>(method, model), changedListener);
        }

        @Override
        public void handleGetter(final Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, TextView view, final Object model, String propertyName, int boundIndex) {
            ModelBinder.bindTextViewToModel(view, propertyName, boundIndex, new IndexedModelGetterImpl<>(method, model), changedListener);
        }

        @Override
        public boolean isOptionalFromAnnotation(Annotation annotation) {
            return ((BindTextView) annotation).isOptional();
        }
    }

    /**
     * AnnotationHandler for OnClick Binding.
     */
    private static class BindOnClickAnnotationHandler implements AnnotationHandler<View> {

        @Override
        public int getIdFromAnnotation(Context context, Annotation annotation) {
            int id = ((BindOnClick) annotation).id();
            if (id != 0) {
                return id;
            }
            return context.getResources().getIdentifier(((BindOnClick) annotation).idName(), "id", context.getPackageName());
        }

        @Override
        public String getPropertyFromAnnotation(Annotation annotation) {
            return null;
        }

        @Override
        public boolean isCallbackHandler() {
            return true;
        }

        @Override
        public Class<? extends Annotation> getAnnotationClass() {
            return BindOnClick.class;
        }

        @Override
        public void handleSetter(final Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, View view, final Object model, String propertyName, int boundIndex) {
            // hard visibility is not interactive
        }

        @Override
        public void handleGetter(final Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, View view, final Object model, String propertyName, int boundIndex) {
            ModelBinder.bindModelToButton(view, boundIndex, new IndexedModelCallbackImpl(method, model));
        }


        @Override
        public boolean isOptionalFromAnnotation(Annotation annotation) {
            return ((BindOnClick) annotation).isOptional();
        }
    }

    /**
     * AnnotationHandler for {@link BindProgress} Binding.
     */
    private static class BindProgressAnnotationHandler implements AnnotationHandler<TextView> {

        @Override
        public Class<? extends Annotation> getAnnotationClass() {
            return BindProgress.class;
        }

        @Override
        public void handleSetter(Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, TextView view, Object model, String propertyName, int index) {
            // enabled state is not interactive
        }

        @Override
        public void handleGetter(Method method, ModelBinder.BindingOnPropertyChangedListener changedListener, TextView view, Object model, String propertyName, int index) {
            ModelBinder.bindProgressToModel(view, propertyName, index, new IndexedModelGetterImpl<>(method, model), changedListener);
        }

        @Override
        public int getIdFromAnnotation(Context context, Annotation annotation) {
            return ((BindProgress) annotation).id();
        }

        @Override
        public String getPropertyFromAnnotation(Annotation annotation) {
            return ((BindProgress) annotation).property();
        }

        @Override
        public boolean isCallbackHandler() {
            return false;
        }

        @Override
        public boolean isOptionalFromAnnotation(Annotation annotation) {
            return ((BindProgress) annotation).isOptional();
        }
    }
}