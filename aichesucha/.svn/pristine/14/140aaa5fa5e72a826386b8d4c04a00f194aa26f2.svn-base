package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.PayWebViewModule;
import com.car.scth.mvp.contract.PayWebViewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.PayWebViewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/01/2021 14:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PayWebViewModule.class, dependencies = AppComponent.class)
public interface PayWebViewComponent {
    void inject(PayWebViewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PayWebViewComponent.Builder view(PayWebViewContract.View view);

        PayWebViewComponent.Builder appComponent(AppComponent appComponent);

        PayWebViewComponent build();
    }
}