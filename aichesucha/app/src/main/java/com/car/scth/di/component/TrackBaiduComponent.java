package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.TrackBaiduModule;
import com.car.scth.mvp.contract.TrackBaiduContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.TrackBaiduActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TrackBaiduModule.class, dependencies = AppComponent.class)
public interface TrackBaiduComponent {
    void inject(TrackBaiduActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrackBaiduComponent.Builder view(TrackBaiduContract.View view);

        TrackBaiduComponent.Builder appComponent(AppComponent appComponent);

        TrackBaiduComponent build();
    }
}