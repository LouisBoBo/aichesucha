package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RealTimeTrackBaiduModule;
import com.car.scth.mvp.contract.RealTimeTrackBaiduContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RealTimeTrackBaiduActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/01/2021 09:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RealTimeTrackBaiduModule.class, dependencies = AppComponent.class)
public interface RealTimeTrackBaiduComponent {
    void inject(RealTimeTrackBaiduActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RealTimeTrackBaiduComponent.Builder view(RealTimeTrackBaiduContract.View view);
        RealTimeTrackBaiduComponent.Builder appComponent(AppComponent appComponent);
        RealTimeTrackBaiduComponent build();
    }
}