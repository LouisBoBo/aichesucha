package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.FenceModule;
import com.car.scth.mvp.contract.FenceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.FenceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FenceModule.class, dependencies = AppComponent.class)
public interface FenceComponent {
    void inject(FenceActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        FenceComponent.Builder view(FenceContract.View view);
        FenceComponent.Builder appComponent(AppComponent appComponent);
        FenceComponent build();
    }
}