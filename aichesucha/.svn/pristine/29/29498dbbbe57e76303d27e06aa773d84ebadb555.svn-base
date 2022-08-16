package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.GroupManagementModule;
import com.car.scth.mvp.contract.GroupManagementContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.GroupManagementActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2021 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupManagementModule.class, dependencies = AppComponent.class)
public interface GroupManagementComponent {
    void inject(GroupManagementActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupManagementComponent.Builder view(GroupManagementContract.View view);

        GroupManagementComponent.Builder appComponent(AppComponent appComponent);

        GroupManagementComponent build();
    }
}