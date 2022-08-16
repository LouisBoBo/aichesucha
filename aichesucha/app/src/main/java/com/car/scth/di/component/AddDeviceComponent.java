package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AddDeviceModule;
import com.car.scth.mvp.contract.AddDeviceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.AddDeviceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2021 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddDeviceModule.class, dependencies = AppComponent.class)
public interface AddDeviceComponent {
    void inject(AddDeviceActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AddDeviceComponent.Builder view(AddDeviceContract.View view);
        AddDeviceComponent.Builder appComponent(AppComponent appComponent);
        AddDeviceComponent build();
    }
}