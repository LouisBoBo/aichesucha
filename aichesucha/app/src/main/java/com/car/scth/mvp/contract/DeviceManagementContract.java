package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.DeviceListForManagerPutBean;
import com.car.scth.mvp.model.putbean.FreezeEquipmentPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.model.putbean.TransferDevicePutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface DeviceManagementContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        /**
         * 获取分组下的设备列表
         * @param deviceListForManagerResultBean
         * @param isRefresh 是否刷新数据
         */
        void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh);

        void finishRefresh();

        void endLoadMore();

        void setNoMore();

        void endLoadFail();

        void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean);

        void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void submitTransferDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean);

        /**
         * 刷新数据
         */
        void onRefreshData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取分组下的设备列表
         * @param bean
         * @return
         */
        Observable<DeviceListForManagerResultBean> getDeviceListForGroup(DeviceListForManagerPutBean bean);

        /**
         * 获取车组织列表和车组列表
         * @param bean
         * @return
         */
        Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean);

        /**
         * 删除设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitDeleteDevice(RemoveDevicePutBean bean);

        /**
         * 转移设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitTransferDevice(TransferDevicePutBean bean);

        /**
         * 冻结设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitFreezeEquipment(FreezeEquipmentPutBean bean);

        /**
         * 获取任务进度
         * @param bean
         * @return
         */
        Observable<TaskProgressResultBean> getTaskProgress(TaskProgressPubBean bean);

    }
}
