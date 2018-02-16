package com.evia.dagger2sampleapplication;

import com.evia.dagger2sampleapplication.di.ApplicationComponent;

/**
 * Created by Evgenii Iashin on 05.02.18.
 */
public class MockApplication extends SampleApplication {

    private ApplicationComponent applicationComponent;

    void setMockApplicationComponent(ApplicationComponent component) {
        applicationComponent = component;
        applicationComponent.inject(this);
    }

    @Override
    protected ApplicationComponent createApplicationComponent() {
        return applicationComponent != null ? applicationComponent : super.createApplicationComponent();
    }
}
