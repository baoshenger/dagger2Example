package com.evia.dagger2sampleapplication.clicker.global;

import com.evia.dagger2sampleapplication.common.Logger;
import com.evia.dagger2sampleapplication.clicker.BaseClickCounter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *  Globally stored click counter
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@Singleton
public class GlobalClickCounter extends BaseClickCounter {

    @Inject
    public GlobalClickCounter(GlobalClickStorage globalClickStorage, Logger logger) {
        super("Global", globalClickStorage, logger);
    }
}
