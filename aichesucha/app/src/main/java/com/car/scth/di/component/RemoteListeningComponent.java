package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RemoteListeningModule;
import com.car.scth.mvp.contract.RemoteListeningContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RemoteListeningActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RemoteListeningModule.class, dependencies = AppComponent.class)
public interface RemoteListeningComponent {
    void inject(RemoteListeningActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RemoteListeningComponent.Builder view(RemoteListeningContract.View view);

        RemoteListeningComponent.Builder appComponent(AppComponent appComponent);

        RemoteListeningComponent build();
    }
}