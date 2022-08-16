package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.LoopModeAddModule;
import com.car.scth.mvp.contract.LoopModeAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.LoopModeAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 17:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LoopModeAddModule.class, dependencies = AppComponent.class)
public interface LoopModeAddComponent {
    void inject(LoopModeAddActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LoopModeAddComponent.Builder view(LoopModeAddContract.View view);
        LoopModeAddComponent.Builder appComponent(AppComponent appComponent);
        LoopModeAddComponent build();
    }
}