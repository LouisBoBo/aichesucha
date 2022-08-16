package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.car.scth.mvp.model.bean.RealTimeModeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RealTimeModePutBean;
import com.car.scth.mvp.model.putbean.RealTimeModeSetPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/14/2020 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface RealTimeModeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getRealTimeModeSuccess(RealTimeModeResultBean realTimeModeResultBean);

        void submitRealTimeModeSuccess(BaseBean baseBean);

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取实时定位模式
         * @param bean
         * @return
         */
        Observable<RealTimeModeResultBean> getRealTimeMode(RealTimeModePutBean bean);

        /**
         * 提交实时定位模式
         * @param bean
         * @return
         */
        Observable<BaseBean> submitRealTimeMode(RealTimeModeSetPutBean bean);

    }
}
