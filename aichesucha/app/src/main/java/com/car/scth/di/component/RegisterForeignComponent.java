package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RegisterForeignModule;
import com.car.scth.mvp.contract.RegisterForeignContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RegisterForeignActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/09/2021 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RegisterForeignModule.class, dependencies = AppComponent.class)
public interface RegisterForeignComponent {
    void inject(RegisterForeignActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RegisterForeignComponent.Builder view(RegisterForeignContract.View view);
        RegisterForeignComponent.Builder appComponent(AppComponent appComponent);
        RegisterForeignComponent build();
    }
}