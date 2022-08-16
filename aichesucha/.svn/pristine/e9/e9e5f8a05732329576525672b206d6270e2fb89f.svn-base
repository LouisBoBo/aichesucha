package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.FindDeviceResultBean;
import com.car.scth.mvp.model.bean.GroupAddResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.FindDevicePutBean;
import com.car.scth.mvp.model.putbean.FreezeEquipmentPutBean;
import com.car.scth.mvp.model.putbean.GroupAddPutBean;
import com.car.scth.mvp.model.putbean.GroupDeletePutBean;
import com.car.scth.mvp.model.putbean.GroupEditPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.model.putbean.TransferDevicePutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2021 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface GroupManagementContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh);

        void finishRefresh();
        /**
         *
         * @param type 请求类型，0：分组列表，1：设备列表，2：搜索设备列表
         */
        void endLoadMore(int type);

        void setNoMore(int type);

        void endLoadFail(int type);

        void submitGroupAddSuccess(GroupAddResultBean groupAddResultBean);

        void submitGroupEditSuccess(BaseBean baseBean, String name, String gid);

        void submitTransferDeviceSuccess(DeviceBaseResultBean transferDeviceResultBean);

        void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void submitDeleteGroupSuccess(BaseBean baseBean);

        void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean);

        void getFindDeviceDataSuccess(FindDeviceResultBean bean);

        /**
         * 关闭加载框
         */
        void onDismissProgress();

        /**
         * 刷新数据
         */
        void onRefreshData();

        /**
         * 获取设备是否有有效定位数据
         * @param deviceDetailResultBean
         * @param imei
         * @param simei
         */
        void getDeviceHasLocationSuccess(DeviceDetailResultBean deviceDetailResultBean, long imei, String simei);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取车组织列表和车组列表
         * @param bean
         * @return
         */
        Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean);

        /**
         * 添加分组
         * @param bean
         * @return
         */
        Observable<GroupAddResultBean> submitGroupAdd(GroupAddPutBean bean);

        /**
         * 编辑分组名称
         * @param bean
         * @return
         */
        Observable<BaseBean> submitGroupEdit(GroupEditPutBean bean);

        /**
         * 转移设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitTransferDevice(TransferDevicePutBean bean);

        /**
         * 删除设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitDeleteDevice(RemoveDevicePutBean bean);

        /**
         * 删除分组
         * @param bean
         * @return
         */
        Observable<BaseBean> submitDeleteGroup(GroupDeletePutBean bean);

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

        /**
         * 模糊，精准搜索设备
         * @param bean
         * @return
         */
        Observable<FindDeviceResultBean> getFindDeviceData(FindDevicePutBean bean);


        /**
         * 获取设备详情接口
         * @param bean
         * @return
         */
        Observable<DeviceDetailResultBean> getDeviceDetailInfo(DeviceDetailPutBean bean);

    }
}
