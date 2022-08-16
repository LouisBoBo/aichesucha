package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RealTimeModeModule;
import com.car.scth.mvp.contract.RealTimeModeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RealTimeModeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RealTimeModeModule.class, dependencies = AppComponent.class)
public interface RealTimeModeComponent {
    void inject(RealTimeModeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RealTimeModeComponent.Builder view(RealTimeModeContract.View view);
        RealTimeModeComponent.Builder appComponent(AppComponent appComponent);
        RealTimeModeComponent build();
    }
}