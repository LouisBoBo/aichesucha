package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.ListeningListModule;
import com.car.scth.mvp.contract.ListeningListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.ListeningListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ListeningListModule.class, dependencies = AppComponent.class)
public interface ListeningListComponent {
    void inject(ListeningListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ListeningListComponent.Builder view(ListeningListContract.View view);

        ListeningListComponent.Builder appComponent(AppComponent appComponent);

        ListeningListComponent build();
    }
}