package com.evia.dagger2sampleapplication.clicker.global;

import com.evia.dagger2sampleapplication.clicker.ClickerResultMapper;
import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *  Module for Global dependencies
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@Module
public class GlobalModule {

    @Provides
    @Named("global")
    static ClickerUseCase provideClickCounter(GlobalClickCounter globalClickCounter) {
        return new ClickerUseCase(globalClickCounter);
    }

    @Provides
    @Named("global")
    static ClickerResultMapper provideClickResultMapper() {
        return new ClickerResultMapper("Global");
    }
}
