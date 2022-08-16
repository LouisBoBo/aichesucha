package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.UidInfoResultBean;
import com.car.scth.mvp.model.putbean.CheckAppUpdatePutBean;
import com.car.scth.mvp.model.putbean.UidInfoPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/14/2020 09:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean);

        void getUidInfoSuccess(UidInfoResultBean uidInfoResultBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取版本更新
         * @param bean
         * @return
         */
        Observable<CheckAppUpdateBean> getAppUpdate(CheckAppUpdatePutBean bean);

        /**
         * 获取推送开关等信息
         * @param bean
         * @return
         */
        Observable<UidInfoResultBean> getUidInfo(UidInfoPutBean bean);

    }
}
