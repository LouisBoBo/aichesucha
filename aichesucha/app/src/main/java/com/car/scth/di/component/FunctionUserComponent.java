package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.FunctionUserModule;
import com.car.scth.mvp.contract.FunctionUserContract;

import com.jess.arms.di.scope.FragmentScope;
import com.car.scth.mvp.ui.fragment.FunctionUserFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 11:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = FunctionUserModule.class, dependencies = AppComponent.class)
public interface FunctionUserComponent {
    void inject(FunctionUserFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        FunctionUserComponent.Builder view(FunctionUserContract.View view);
        FunctionUserComponent.Builder appComponent(AppComponent appComponent);
        FunctionUserComponent build();
    }
}