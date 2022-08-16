package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.car.scth.mvp.model.bean.AccountUserListResultBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.GetTaskResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AccountUserInfoPutBean;
import com.car.scth.mvp.model.putbean.DeleteFamilyPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.EditFamilyPutBean;
import com.car.scth.mvp.model.putbean.GetTaskPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface UserManagementContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {


        void getFamilyListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh, String sid, int level);

        void editFamilyNameSuccess(BaseBean bean, String sid, String name);

        void deleteFamilySuccess(DeviceBaseResultBean deviceBaseResultBean);

        void getTaskSuccess(GetTaskResultBean bean);

        void finishRefresh();

        void endLoadMore();

        void setNoMore();

        void endLoadFail();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取用户列表 暂不使用
         *
         * @return
         */
        Observable<AccountUserListResultBean> getAccountList(AccountUserInfoPutBean bean);


        /**
         * 获取车组织列表和车组列表
         *
         * @param bean
         * @return
         */
        Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean);


        /**
         * 修改组织名称
         *
         * @param bean
         * @return
         */
        Observable<BaseBean> submitEditFamilyName(EditFamilyPutBean bean);


        /**
         * 删除组织
         *
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitDeleteFamily(DeleteFamilyPutBean bean);

        /**
         * 获取任务执行结果
         * @param bean
         * @return
         */
        Observable<GetTaskResultBean>  getTaskResult(GetTaskPutBean bean);



    }
}
