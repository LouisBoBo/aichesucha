package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.SysSettingActivityModule;
import com.car.scth.mvp.contract.SysSettingActivityContract;

import com.jess.arms.di.scope.ActivityScope;

import com.car.scth.mvp.ui.activity.SysSettingActivityActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2021 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SysSettingActivityModule.class, dependencies = AppComponent.class)
public interface SysSettingActivityComponent {
    void inject(SysSettingActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SysSettingActivityComponent.Builder view(SysSettingActivityContract.View view);

        SysSettingActivityComponent.Builder appComponent(AppComponent appComponent);

        SysSettingActivityComponent build();
    }
}