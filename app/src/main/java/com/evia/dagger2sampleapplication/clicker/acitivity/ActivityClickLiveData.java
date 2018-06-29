package com.evia.dagger2sampleapplication.clicker.acitivity;

import com.evia.dagger2sampleapplication.clicker.ClickerUseCase;
import com.evia.dagger2sampleapplication.clicker.global.GlobalClickLiveData;
import com.evia.dagger2sampleapplication.common.di.scope.ActivityScope;
import com.evia.dagger2sampleapplication.common.usecase.BaseUseCaseLiveData;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *  Live data for {@link ActivityClickCounter}
 *  NOTE: will update {@link GlobalClickLiveData}
 *
 * Created by Evgenii Iashin on 25.06.18.
 */
@ActivityScope
public class ActivityClickLiveData extends BaseUseCaseLiveData<Void, Integer, Void> {

    private final GlobalClickLiveData globalClickLiveData;

    @Inject
    public ActivityClickLiveData(@Named("activity") ClickerUseCase clickerUseCase, ActivityClickStorage storage, GlobalClickLiveData globalClickLiveData) {
        super(clickerUseCase, storage.getClicks());
        this.globalClickLiveData = globalClickLiveData;
    }

    @Override
    public void execute(Void aVoid) {
        globalClickLiveData.execute(aVoid);
        super.execute(aVoid);
    }
}
