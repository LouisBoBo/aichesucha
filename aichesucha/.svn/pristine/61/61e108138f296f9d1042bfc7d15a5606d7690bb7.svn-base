package com.car.scth.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.car.scth.di.module.BaiduPanoramaModule;
import com.car.scth.mvp.contract.BaiduPanoramaContract;

import com.jess.arms.di.scope.ActivityScope;
import com.car.scth.mvp.ui.activity.BaiduPanoramaActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/22/2021 17:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BaiduPanoramaModule.class, dependencies = AppComponent.class)
public interface BaiduPanoramaComponent {
    void inject(BaiduPanoramaActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        BaiduPanoramaComponent.Builder view(BaiduPanoramaContract.View view);
        BaiduPanoramaComponent.Builder appComponent(AppComponent appComponent);
        BaiduPanoramaComponent build();
    }
}