package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.car.scth.mvp.model.bean.TrackHasDataResultBean;
import com.car.scth.mvp.model.bean.TrackListResultBean;
import com.car.scth.mvp.model.putbean.TrackHasDataPutBean;
import com.car.scth.mvp.model.putbean.TrackListPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/04/2020 11:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface TrackAmapContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getTrackHasForDataSuccess(TrackHasDataResultBean trackHasDataResultBean);

        void getTrackListSuccess(TrackListResultBean trackListResultBean, boolean isResetData);

        /**
         * 没有更多数据了
         */
        void onEndMoreData();

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取有轨迹数据的日期
         * @param bean
         * @return
         */
        Observable<TrackHasDataResultBean> getTrackHasForData(TrackHasDataPutBean bean);

        /**
         * 获取轨迹列表数据
         * @param bean
         * @return
         */
        Observable<TrackListResultBean> getTrackList(TrackListPutBean bean);

    }
}
