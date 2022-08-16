package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.ModifyPhoneModule;
import com.car.scth.mvp.contract.ModifyPhoneContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.ModifyPhoneActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/02/2021 15:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ModifyPhoneModule.class, dependencies = AppComponent.class)
public interface ModifyPhoneComponent {
    void inject(ModifyPhoneActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ModifyPhoneComponent.Builder view(ModifyPhoneContract.View view);
        ModifyPhoneComponent.Builder appComponent(AppComponent appComponent);
        ModifyPhoneComponent build();
    }
}