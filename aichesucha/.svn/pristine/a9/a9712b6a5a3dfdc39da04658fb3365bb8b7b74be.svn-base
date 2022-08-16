package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.car.scth.mvp.model.bean.RecordConfigResultBean;
import com.car.scth.mvp.model.bean.RecordResultBean;
import com.car.scth.mvp.model.bean.RecordScheduleResultBean;
import com.car.scth.mvp.model.bean.RecordShortResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RecordConfigPutBean;
import com.car.scth.mvp.model.putbean.RecordDeletePutBean;
import com.car.scth.mvp.model.putbean.RecordPutBean;
import com.car.scth.mvp.model.putbean.RecordSchedulePutBean;
import com.car.scth.mvp.model.putbean.RecordShortPutBean;
import com.car.scth.mvp.model.putbean.RecordShortResultPutBean;
import com.car.scth.mvp.model.putbean.RecordStopPutBean;
import com.car.scth.mvp.model.putbean.VoiceRecordingPutBean;

import io.reactivex.Observable;


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
public interface RecordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getRecordConfigSuccess(RecordConfigResultBean recordConfigResultBean);

        void getRecordDataSuccess(RecordResultBean recordResultBean, boolean isHistoryData);

        void finishRefresh();

        void submitAutoRecordSwitchSuccess(BaseBean baseBean, boolean switchX);

        void submitShortRecordSuccess(BaseBean baseBean);

        void getShortRecordResultSuccess(RecordShortResultBean recordShortResultBean);

        void getRecordScheduleSuccess(RecordScheduleResultBean recordScheduleResultBean);

        void submitDeleteRecordSuccess(BaseBean baseBean);

        void submitRecordStopSuccess(BaseBean baseBean);

        /**
         * 关闭加载框
         */
        void dismissDialog();
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取录音配置参数
         * @param bean
         * @return
         */
        Observable<RecordConfigResultBean> getRecordConfig(RecordConfigPutBean bean);

        /**
         * 获取录音数据
         * @param bean
         * @return
         */
        Observable<RecordResultBean> getRecordData(RecordPutBean bean);

        /**
         * 开启or关闭声控录音
         * @param bean
         * @return
         */
        Observable<BaseBean> submitAutoRecordSwitch(VoiceRecordingPutBean bean);

        /**
         * 开始短录音
         * @param bean
         * @return
         */
        Observable<BaseBean> submitShortRecord(RecordShortPutBean bean);

        /**
         * 获取开始短录音指令下发结果
         * @param bean
         * @return
         */
        Observable<RecordShortResultBean> getShortRecordResult(RecordShortResultPutBean bean);

        /**
         * 获取录音进度结果
         * @param bean
         * @return
         */
        Observable<RecordScheduleResultBean> getRecordSchedule(RecordSchedulePutBean bean);

        /**
         * 删除录音文件
         * @param bean
         * @return
         */
        Observable<BaseBean> submitDeleteRecord(RecordDeletePutBean bean);

        /**
         * 停止录音
         * @param bean
         * @return
         */
        Observable<BaseBean> submitRecordStop(RecordStopPutBean bean);

    }
}
