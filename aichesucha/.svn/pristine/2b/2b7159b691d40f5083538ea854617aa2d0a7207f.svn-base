package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.OperationRecordModule;
import com.car.scth.mvp.contract.OperationRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.OperationRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OperationRecordModule.class, dependencies = AppComponent.class)
public interface OperationRecordComponent {
    void inject(OperationRecordActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        OperationRecordComponent.Builder view(OperationRecordContract.View view);
        OperationRecordComponent.Builder appComponent(AppComponent appComponent);
        OperationRecordComponent build();
    }
}