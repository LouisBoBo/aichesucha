package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.DeviceDetailModule;
import com.car.scth.mvp.contract.DeviceDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.DeviceDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DeviceDetailModule.class, dependencies = AppComponent.class)
public interface DeviceDetailComponent {
    void inject(DeviceDetailActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        DeviceDetailComponent.Builder view(DeviceDetailContract.View view);
        DeviceDetailComponent.Builder appComponent(AppComponent appComponent);
        DeviceDetailComponent build();
    }
}