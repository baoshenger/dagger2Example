package com.evia.dagger2sampleapplication.clicker.global;

import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.common.usecase.BaseUseCaseLiveData;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *  Live data for {@link GlobalClickCounter}
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@Singleton
public class GlobalClickLiveData extends BaseUseCaseLiveData<Void, Integer, Void> {

    @Inject
    public GlobalClickLiveData(@Named("global") ClickerUseCase globalClickerUseCase, GlobalClickStorage storage) {
        super(globalClickerUseCase, storage.getClicks());
    }
}
