package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.BindMobileModule;
import com.car.scth.mvp.contract.BindMobileContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.BindMobileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2021 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BindMobileModule.class, dependencies = AppComponent.class)
public interface BindMobileComponent {
    void inject(BindMobileActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindMobileComponent.Builder view(BindMobileContract.View view);

        BindMobileComponent.Builder appComponent(AppComponent appComponent);

        BindMobileComponent build();
    }
}