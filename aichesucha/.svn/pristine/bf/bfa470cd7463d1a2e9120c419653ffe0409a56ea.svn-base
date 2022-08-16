package com.car.scth.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.car.scth.mvp.model.bean.AccountListResultBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.AccountDeletePutBean;
import com.car.scth.mvp.model.putbean.AccountListPutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 11:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AccountListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getAccountListNextSuccess(AccountListResultBean accountListResultBean, boolean isRefresh);

        void finishRefresh();

        void endLoadMore();

        void setNoMore();

        void endLoadFail();

        void submitAccountDeleteSuccess(DeviceBaseResultBean deviceBaseResultBean, String uid);

        void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean);

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取当前组织的下级用户列表
         * @param bean
         * @return
         */
        Observable<AccountListResultBean> getAccountListNext(AccountListPutBean bean);

        /**
         * 删除账号
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitAccountDelete(AccountDeletePutBean bean);

        /**
         * 获取任务进度
         * @param bean
         * @return
         */
        Observable<TaskProgressResultBean> getTaskProgress(TaskProgressPubBean bean);

    }
}
