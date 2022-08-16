package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AlarmScreenModule;
import com.car.scth.mvp.contract.AlarmScreenContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.AlarmScreenActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2021 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmScreenModule.class, dependencies = AppComponent.class)
public interface AlarmScreenComponent {
    void inject(AlarmScreenActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmScreenComponent.Builder view(AlarmScreenContract.View view);

        AlarmScreenComponent.Builder appComponent(AppComponent appComponent);

        AlarmScreenComponent build();
    }
}