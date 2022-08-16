package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.ForgetPasswordModule;
import com.car.scth.mvp.contract.ForgetPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.ForgetPasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ForgetPasswordModule.class, dependencies = AppComponent.class)
public interface ForgetPasswordComponent {
    void inject(ForgetPasswordActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPasswordComponent.Builder view(ForgetPasswordContract.View view);
        ForgetPasswordComponent.Builder appComponent(AppComponent appComponent);
        ForgetPasswordComponent build();
    }
}