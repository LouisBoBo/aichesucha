package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RecycleBinModule;
import com.car.scth.mvp.contract.RecycleBinContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RecycleBinActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/05/2021 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RecycleBinModule.class, dependencies = AppComponent.class)
public interface RecycleBinComponent {
    void inject(RecycleBinActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RecycleBinComponent.Builder view(RecycleBinContract.View view);
        RecycleBinComponent.Builder appComponent(AppComponent appComponent);
        RecycleBinComponent build();
    }
}