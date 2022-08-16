package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.DataCenterModule;
import com.car.scth.mvp.contract.DataCenterContract;

import com.jess.arms.di.scope.FragmentScope;
import com.car.scth.mvp.ui.fragment.DataCenterFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 11:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DataCenterModule.class, dependencies = AppComponent.class)
public interface DataCenterComponent {
    void inject(DataCenterFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        DataCenterComponent.Builder view(DataCenterContract.View view);
        DataCenterComponent.Builder appComponent(AppComponent appComponent);
        DataCenterComponent build();
    }
}