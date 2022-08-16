package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.HMSScanQrCodeModule;
import com.car.scth.mvp.contract.HMSScanQrCodeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.HMSScanQrCodeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2021 14:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HMSScanQrCodeModule.class, dependencies = AppComponent.class)
public interface HMSScanQrCodeComponent {
    void inject(HMSScanQrCodeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        HMSScanQrCodeComponent.Builder view(HMSScanQrCodeContract.View view);
        HMSScanQrCodeComponent.Builder appComponent(AppComponent appComponent);
        HMSScanQrCodeComponent build();
    }
}