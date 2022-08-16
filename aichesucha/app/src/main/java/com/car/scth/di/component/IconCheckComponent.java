package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.IconCheckModule;
import com.car.scth.mvp.contract.IconCheckContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.IconCheckActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = IconCheckModule.class, dependencies = AppComponent.class)
public interface IconCheckComponent {
    void inject(IconCheckActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        IconCheckComponent.Builder view(IconCheckContract.View view);
        IconCheckComponent.Builder appComponent(AppComponent appComponent);
        IconCheckComponent build();
    }
}