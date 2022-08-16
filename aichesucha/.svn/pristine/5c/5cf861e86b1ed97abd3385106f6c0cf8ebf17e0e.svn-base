package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.PushSwitchModule;
import com.car.scth.mvp.contract.PushSwitchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.PushSwitchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/03/2021 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PushSwitchModule.class, dependencies = AppComponent.class)
public interface PushSwitchComponent {
    void inject(PushSwitchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PushSwitchComponent.Builder view(PushSwitchContract.View view);

        PushSwitchComponent.Builder appComponent(AppComponent appComponent);

        PushSwitchComponent build();
    }
}