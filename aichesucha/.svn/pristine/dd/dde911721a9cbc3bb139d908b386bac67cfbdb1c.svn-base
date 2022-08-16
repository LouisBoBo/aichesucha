package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RealTimeTrackAmapModule;
import com.car.scth.mvp.contract.RealTimeTrackAmapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RealTimeTrackAmapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RealTimeTrackAmapModule.class, dependencies = AppComponent.class)
public interface RealTimeTrackAmapComponent {
    void inject(RealTimeTrackAmapActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RealTimeTrackAmapComponent.Builder view(RealTimeTrackAmapContract.View view);
        RealTimeTrackAmapComponent.Builder appComponent(AppComponent appComponent);
        RealTimeTrackAmapComponent build();
    }
}