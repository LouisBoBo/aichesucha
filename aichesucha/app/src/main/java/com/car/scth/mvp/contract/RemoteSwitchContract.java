package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.car.scth.mvp.model.bean.RemoteSwitchResultBean;
import com.car.scth.mvp.model.bean.TimeSwitchGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RemoteSwitchSetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchDeletePutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchGetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchSetPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface RemoteSwitchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void submitRemoteSwitchSuccess(RemoteSwitchResultBean remoteSwitchResultBean);

        void getTimeSwitchSuccess(TimeSwitchGetResultBean timeSwitchGetResultBean);

        void submitTimeSwitchSuccess(BaseBean baseBean);

        void submitTimeSwitchDeleteSuccess(BaseBean baseBean);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 远程开关机设置
         * @param bean
         * @return
         */
        Observable<RemoteSwitchResultBean> submitRemoteSwitch(RemoteSwitchSetPutBean bean);

        /**
         * 获取定时开关机数据
         * @param bean
         * @return
         */
        Observable<TimeSwitchGetResultBean> getTimeSwitch(TimeSwitchGetPutBean bean);

        /**
         * 设置定时开关机
         * @param bean
         * @return
         */
        Observable<BaseBean> submitTimeSwitch(TimeSwitchSetPutBean bean);

        /**
         * 删除定时开关机
         * @param bean
         * @return
         */
        Observable<BaseBean> submitTimeSwitchDelete(TimeSwitchDeletePutBean bean);

    }
}
