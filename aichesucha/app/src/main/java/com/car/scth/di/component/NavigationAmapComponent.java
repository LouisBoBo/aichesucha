package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.NavigationAmapModule;
import com.car.scth.mvp.contract.NavigationAmapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.NavigationAmapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NavigationAmapModule.class, dependencies = AppComponent.class)
public interface NavigationAmapComponent {
    void inject(NavigationAmapActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        NavigationAmapComponent.Builder view(NavigationAmapContract.View view);
        NavigationAmapComponent.Builder appComponent(AppComponent appComponent);
        NavigationAmapComponent build();
    }
}