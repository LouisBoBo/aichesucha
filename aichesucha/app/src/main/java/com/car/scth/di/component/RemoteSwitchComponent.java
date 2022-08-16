package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RemoteSwitchModule;
import com.car.scth.mvp.contract.RemoteSwitchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RemoteSwitchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 16:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RemoteSwitchModule.class, dependencies = AppComponent.class)
public interface RemoteSwitchComponent {
    void inject(RemoteSwitchActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RemoteSwitchComponent.Builder view(RemoteSwitchContract.View view);
        RemoteSwitchComponent.Builder appComponent(AppComponent appComponent);
        RemoteSwitchComponent build();
    }
}