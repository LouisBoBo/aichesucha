package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AlarmMessageUserModule;
import com.car.scth.mvp.contract.AlarmMessageUserContract;

import com.jess.arms.di.scope.FragmentScope;
import com.car.scth.mvp.ui.fragment.AlarmMessageUserFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AlarmMessageUserModule.class, dependencies = AppComponent.class)
public interface AlarmMessageUserComponent {
    void inject(AlarmMessageUserFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmMessageUserComponent.Builder view(AlarmMessageUserContract.View view);
        AlarmMessageUserComponent.Builder appComponent(AppComponent appComponent);
        AlarmMessageUserComponent build();
    }
}