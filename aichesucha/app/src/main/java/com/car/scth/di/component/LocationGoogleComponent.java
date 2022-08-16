package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.LocationGoogleModule;
import com.car.scth.mvp.contract.LocationGoogleContract;

import com.jess.arms.di.scope.FragmentScope;
import com.car.scth.mvp.ui.fragment.LocationGoogleFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = LocationGoogleModule.class, dependencies = AppComponent.class)
public interface LocationGoogleComponent {
    void inject(LocationGoogleFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LocationGoogleComponent.Builder view(LocationGoogleContract.View view);
        LocationGoogleComponent.Builder appComponent(AppComponent appComponent);
        LocationGoogleComponent build();
    }
}