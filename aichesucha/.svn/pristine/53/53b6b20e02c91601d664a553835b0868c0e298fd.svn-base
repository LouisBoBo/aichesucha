package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.TrackAmapModule;
import com.car.scth.mvp.contract.TrackAmapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.TrackAmapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TrackAmapModule.class, dependencies = AppComponent.class)
public interface TrackAmapComponent {
    void inject(TrackAmapActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        TrackAmapComponent.Builder view(TrackAmapContract.View view);
        TrackAmapComponent.Builder appComponent(AppComponent appComponent);
        TrackAmapComponent build();
    }
}