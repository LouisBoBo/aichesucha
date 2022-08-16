package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.ModifyPasswordModule;
import com.car.scth.mvp.contract.ModifyPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.ModifyPasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/18/2021 16:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ModifyPasswordModule.class, dependencies = AppComponent.class)
public interface ModifyPasswordComponent {
    void inject(ModifyPasswordActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ModifyPasswordComponent.Builder view(ModifyPasswordContract.View view);
        ModifyPasswordComponent.Builder appComponent(AppComponent appComponent);
        ModifyPasswordComponent build();
    }
}