package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.FunctionModule;
import com.car.scth.mvp.contract.FunctionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.FunctionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 16:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FunctionModule.class, dependencies = AppComponent.class)
public interface FunctionComponent {
    void inject(FunctionActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        FunctionComponent.Builder view(FunctionContract.View view);
        FunctionComponent.Builder appComponent(AppComponent appComponent);
        FunctionComponent build();
    }
}