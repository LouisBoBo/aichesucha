package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.LocationAmapModule;
import com.car.scth.mvp.contract.LocationAmapContract;

import com.jess.arms.di.scope.FragmentScope;
import com.car.scth.mvp.ui.fragment.LocationAmapFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = LocationAmapModule.class, dependencies = AppComponent.class)
public interface LocationAmapComponent {
    void inject(LocationAmapFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LocationAmapComponent.Builder view(LocationAmapContract.View view);

        LocationAmapComponent.Builder appComponent(AppComponent appComponent);

        LocationAmapComponent build();
    }
}