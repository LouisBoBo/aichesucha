package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.FenceCreateBaiduModule;
import com.car.scth.mvp.contract.FenceCreateBaiduContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.FenceCreateBaiduActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FenceCreateBaiduModule.class, dependencies = AppComponent.class)
public interface FenceCreateBaiduComponent {
    void inject(FenceCreateBaiduActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FenceCreateBaiduComponent.Builder view(FenceCreateBaiduContract.View view);

        FenceCreateBaiduComponent.Builder appComponent(AppComponent appComponent);

        FenceCreateBaiduComponent build();
    }
}