package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.CustomerWebViewModule;
import com.car.scth.mvp.contract.CustomerWebViewContract;

import com.jess.arms.di.scope.ActivityScope;

import com.car.scth.mvp.ui.activity.CustomerWebViewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/28/2021 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CustomerWebViewModule.class, dependencies = AppComponent.class)
public interface CustomerWebViewComponent {
    void inject(CustomerWebViewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CustomerWebViewComponent.Builder view(CustomerWebViewContract.View view);

        CustomerWebViewComponent.Builder appComponent(AppComponent appComponent);

        CustomerWebViewComponent build();
    }
}