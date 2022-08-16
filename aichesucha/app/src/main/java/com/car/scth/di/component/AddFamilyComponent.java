package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AddFamilyModule;
import com.car.scth.mvp.contract.AddFamilyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.AddFamilyActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/11/2021 16:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddFamilyModule.class, dependencies = AppComponent.class)
public interface AddFamilyComponent {
    void inject(AddFamilyActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AddFamilyComponent.Builder view(AddFamilyContract.View view);
        AddFamilyComponent.Builder appComponent(AppComponent appComponent);
        AddFamilyComponent build();
    }
}