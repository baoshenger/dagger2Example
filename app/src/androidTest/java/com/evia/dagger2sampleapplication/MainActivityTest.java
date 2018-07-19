package com.evia.dagger2sampleapplication;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.evia.dagger2sampleapplication.clicker.BaseClickCounter;
import com.evia.dagger2sampleapplication.clicker.acitivity.ActivityClickCounter;
import com.evia.dagger2sampleapplication.clicker.acitivity.MainActivity;
import com.evia.dagger2sampleapplication.clicker.fragment.FragmentClickCounter;
import com.evia.dagger2sampleapplication.clicker.fragment.MainFragment;
import com.evia.dagger2sampleapplication.clicker.global.GlobalClickCounter;
import com.evia.dagger2sampleapplication.common.di.ApplicationComponent;

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
            Builder addGlobalCounter(@Named("global") BaseClickCounter clickCounter);

            @BindsInstance
            Builder addActivityCounter(@Named("activity") BaseClickCounter clickCounter);

            @BindsInstance
            Builder addFragmentCounter(@Named("fragment") BaseClickCounter clickCounter);

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
//        abstract BaseClickCounter bindGlovalClickCounter(SampleApplication.GlobalClickCounter globalClickCounter);
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
//        @ContributesAndroidInjector(modules = {MainActivityModule.class})
//        abstract MainActivity mainActivity();
//    }

    @Before
    public void setUp() throws Exception {
        MockApplication context = (MockApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();

        GlobalClickCounter globalClickCounter = mock(GlobalClickCounter.class);
        ActivityClickCounter activityClickCounter = mock(ActivityClickCounter.class);
        FragmentClickCounter fragmentClickCounter = mock(FragmentClickCounter.class);

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
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));


        //first clickActivityClicker
        Espresso.onView(withId(R.id.global_counter)).perform(click()).check(matches(withText("Global state : 1 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //second clickActivityClicker
        Espresso.onView(withId(R.id.global_counter)).perform(click()).check(matches(withText("Global state : 2 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));
    }

    @Test
    public void testActivityStateClickRaisesGlobalAndActivityCount() {
        testRule.launchActivity(null);

        //initial state
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 0 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //first clickActivityClicker
        Espresso.onView(withId(R.id.activity_counter)).perform(click()).check(matches(withText("Activity state : 1 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 1 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //second clickActivityClicker
        Espresso.onView(withId(R.id.activity_counter)).perform(click()).check(matches(withText("Activity state : 2 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 2 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));
    }

    @Test
    public void testFragmentStateClickRaisesGlobalAndActivityAndFragmentCount() {
        testRule.launchActivity(null);

        //initial state
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 0 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 0 clicks")));
        Espresso.onView(withId(R.id.main_fragment)).check(matches(withText("Fragment state : 0 clicks")));

        //first clickActivityClicker
        Espresso.onView(withId(R.id.main_fragment)).perform(click()).check(matches(withText("Fragment state : 1 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 1 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 1 clicks")));

        //second clickActivityClicker
        Espresso.onView(withId(R.id.main_fragment)).perform(click()).check(matches(withText("Fragment state : 2 clicks")));
        Espresso.onView(withId(R.id.activity_counter)).check(matches(withText("Activity state : 2 clicks")));
        Espresso.onView(withId(R.id.global_counter)).check(matches(withText("Global state : 2 clicks")));
    }

}