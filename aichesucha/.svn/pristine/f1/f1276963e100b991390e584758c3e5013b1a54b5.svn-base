package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.RecordModule;
import com.car.scth.mvp.contract.RecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.RecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RecordModule.class, dependencies = AppComponent.class)
public interface RecordComponent {
    void inject(RecordActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RecordComponent.Builder view(RecordContract.View view);
        RecordComponent.Builder appComponent(AppComponent appComponent);
        RecordComponent build();
    }
}