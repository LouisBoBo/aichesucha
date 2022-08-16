package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceConfigPutBean;
import com.car.scth.mvp.model.putbean.DeviceConfigSetPutBean;

import io.reactivex.Observable;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/02/2020 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AlarmSettingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean);

        void setDeviceConfigSuccess(BaseBean baseBean);

        void submitPushSwitchSuccess(boolean pushSwitch, boolean isSetPushSwitch);

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取设备的配置信息，支持的功能等
         * @param bean
         * @return
         */
        Observable<DeviceConfigResultBean> getDeviceConfig(DeviceConfigPutBean bean);

        /**
         * 设置设备的配置信息
         * @param bean
         * @return
         */
        Observable<BaseBean> setDeviceConfig(DeviceConfigSetPutBean bean);

        /**
         * 修改密码
         * @param bean
         * @return
         */
        Observable<BaseBean> submitPushSwitch(ModifyPasswordPutBean bean);

    }
}
