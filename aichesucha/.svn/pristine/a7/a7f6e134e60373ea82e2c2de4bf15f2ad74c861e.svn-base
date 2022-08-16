package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RechargeModule;
import com.car.scth.mvp.contract.RechargeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RechargeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 14:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RechargeModule.class, dependencies = AppComponent.class)
public interface RechargeComponent {
    void inject(RechargeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RechargeComponent.Builder view(RechargeContract.View view);
        RechargeComponent.Builder appComponent(AppComponent appComponent);
        RechargeComponent build();
    }
}