package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.AlarmRecordModule;
import com.car.scth.mvp.contract.AlarmRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.AlarmRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 11:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmRecordModule.class, dependencies = AppComponent.class)
public interface AlarmRecordComponent {
    void inject(AlarmRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmRecordComponent.Builder view(AlarmRecordContract.View view);

        AlarmRecordComponent.Builder appComponent(AppComponent appComponent);

        AlarmRecordComponent build();
    }
}