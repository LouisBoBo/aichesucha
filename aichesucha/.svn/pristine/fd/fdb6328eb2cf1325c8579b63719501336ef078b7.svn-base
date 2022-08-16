package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.FenceCreateAmapModule;
import com.car.scth.mvp.contract.FenceCreateAmapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.FenceCreateAmapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 15:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FenceCreateAmapModule.class, dependencies = AppComponent.class)
public interface FenceCreateAmapComponent {
    void inject(FenceCreateAmapActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        FenceCreateAmapComponent.Builder view(FenceCreateAmapContract.View view);
        FenceCreateAmapComponent.Builder appComponent(AppComponent appComponent);
        FenceCreateAmapComponent build();
    }
}