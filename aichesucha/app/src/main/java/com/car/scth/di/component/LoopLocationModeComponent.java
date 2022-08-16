package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.LoopLocationModeModule;
import com.car.scth.mvp.contract.LoopLocationModeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.LoopLocationModeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 17:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LoopLocationModeModule.class, dependencies = AppComponent.class)
public interface LoopLocationModeComponent {
    void inject(LoopLocationModeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LoopLocationModeComponent.Builder view(LoopLocationModeContract.View view);
        LoopLocationModeComponent.Builder appComponent(AppComponent appComponent);
        LoopLocationModeComponent build();
    }
}