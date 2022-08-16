package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AlarmTimeScreenModule;
import com.car.scth.mvp.contract.AlarmTimeScreenContract;

import com.jess.arms.di.scope.ActivityScope;

import com.car.scth.mvp.ui.activity.AlarmTimeScreenActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2021 17:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmTimeScreenModule.class, dependencies = AppComponent.class)
public interface AlarmTimeScreenComponent {
    void inject(AlarmTimeScreenActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmTimeScreenComponent.Builder view(AlarmTimeScreenContract.View view);

        AlarmTimeScreenComponent.Builder appComponent(AppComponent appComponent);

        AlarmTimeScreenComponent build();
    }
}