package com.evia.dagger2sampleapplication;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.evia.dagger2sampleapplication.clicker.BaseClickObservable;
import com.evia.dagger2sampleapplication.di.ApplicationComponent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;

/**
 * Created by Evgenii Iashin on 05.02.18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, false);

//    @Singleton
//    @Component(modules = {
//            TestApplicationModule.class
//    })
//    public interface TestApplicationComponent extends ApplicationComponent {
//
//
//        @Component.Builder
//        interface Builder {
//            @BindsInstance Builder addAppContext(Application application);
//            TestApplicationComponent build();
//        }
//    }

    @Singleton
    @Component(modules = {
            TestInjector.class,
            AndroidInjectionModule.class})
    public interface TestApplicationComponentDirectInject extends ApplicationComponent {


        @Component.Builder
        interface Builder {
            @BindsInstance
            Builder addAppContext(Application application);

            @BindsInstance
            Builder addGlobalCounter(@Named("global") BaseClickObservable clickCounter);

            @BindsInstance
            Builder addActivityCounter(@Named("activity") BaseClickObservable clickCounter);

            @BindsInstance
            Builder addFragmentCounter(@Named("fragment") BaseClickObservable clickCounter);

            TestApplicationComponentDirectInject build();
        }
    }

    @Module
    public interface TestInjector {
        @ContributesAndroidInjector()
        MainActivity mainActivity();

        @ContributesAndroidInjector()
        MainFragment mainFragment();
    }

//    @Module
//    public static abstract class TestApplicationModule extends ApplicationModule {
//        @Binds
//        @Named("global")
//        abstract BaseClickObservable bindGlovalClickCounter(SampleApplication.GlobalClickCounter globalClickCounter);
//
//        @Binds
//        abstract SharedPreferences provideSharedPreferences(MockSharedPreferences mockSharedPreferences);
//
//        @Provides
//        @Singleton
//        static Logger provideLogger() {
//            return entry -> {};
//        }
//
//        @ActivityScope
//        @ContributesAndroidInjector(modules = {ActivityModule.class})
//        abstract MainActivity mainActivity();
//    }

    @Before
    public void setUp() throws Exception {
        MockApplication context = (MockApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();

        SampleApplication.GlobalClickCounter globalClickCounter = mock(SampleApplication.GlobalClickCounter.class);
        MainActivity.ActivityClickCounter activityClickCounter = mock(MainActivity.ActivityClickCounter.class);
        MainFragment.FragmentClickCounter fragmentClickCounter = mock(MainFragment.FragmentClickCounter.class);

        TestApplicationComponentDirectInject testApplicationComponent = DaggerMainActivityTest_TestApplicationComponentDirectInject.builder()
                .addAppContext(context)
                .addActivityCounter(activityClickCounter)
                .addFragmentCounter(fragmentClickCounter)
                .addGlobalCounter(globalClickCounter)
                .build();

        context.setMockApplicationComponent(testApplicationComponent);
    }

    @Test
    public void testGlobalStateClickRaisesGlobalCount() {
        testRule.launchActivity(null);

        //initial state
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 0 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));


        //first click
        Espresso.onView(withId(R.id.global_counter)).perform(click()).check(matches(withText("Global state : 1 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //second click
        Espresso.onView(withId(R.id.global_counter)).perform(click()).check(matches(withText("Global state : 2 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));
    }

    @Test
    public void testActivityStateClickRaisesGlobalAndActivityCount() {
        testRule.launchActivity(null);

        //initial state
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 0 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //first click
        Espresso.onView(withId(R.id.activity_counter)).perform(click()).check(matches(withText("Activity state : 1 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 1 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //second click
        Espresso.onView(withId(R.id.activity_counter)).perform(click()).check(matches(withText("Activity state : 2 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 2 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));
    }

    @Test
    public void testFragmentStateClickRaisesGlobalAndActivityAndFragmentCount() {
        testRule.launchActivity(null);

        //initial state
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 0 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //first click
        Espresso.onView(withId(R.id.fragment)).perform(click()).check(matches(withText("Fragment state : 1 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 1 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 1 clicks")));

        //second click
        Espresso.onView(withId(R.id.fragment)).perform(click()).check(matches(withText("Fragment state : 2 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 2 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 2 clicks")));
    }

}