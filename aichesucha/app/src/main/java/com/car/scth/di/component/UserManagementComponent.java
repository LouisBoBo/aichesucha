package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.UserManagementModule;
import com.car.scth.mvp.contract.UserManagementContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.UserManagementActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserManagementModule.class, dependencies = AppComponent.class)
public interface UserManagementComponent {
    void inject(UserManagementActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        UserManagementComponent.Builder view(UserManagementContract.View view);
        UserManagementComponent.Builder appComponent(AppComponent appComponent);
        UserManagementComponent build();
    }
}