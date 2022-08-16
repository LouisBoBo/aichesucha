package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AddAccountModule;
import com.car.scth.mvp.contract.AddAccountContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.AddAccountActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddAccountModule.class, dependencies = AppComponent.class)
public interface AddAccountComponent {
    void inject(AddAccountActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AddAccountComponent.Builder view(AddAccountContract.View view);
        AddAccountComponent.Builder appComponent(AppComponent appComponent);
        AddAccountComponent build();
    }
}