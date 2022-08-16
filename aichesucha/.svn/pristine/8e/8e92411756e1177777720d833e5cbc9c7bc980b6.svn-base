package com.car.scth.mvp.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerFunctionUserComponent;
import com.car.scth.mvp.contract.FunctionUserContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.bean.DeviceFunctionBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceConfigPutBean;
import com.car.scth.mvp.model.putbean.OnKeyFunctionPutBean;
import com.car.scth.mvp.presenter.FunctionUserPresenter;
import com.car.scth.mvp.ui.activity.AlarmRecordActivity;
import com.car.scth.mvp.ui.activity.DeviceDetailActivity;
import com.car.scth.mvp.ui.activity.FenceActivity;
import com.car.scth.mvp.ui.activity.IconCheckActivity;
import com.car.scth.mvp.ui.activity.LocationModeActivity;
import com.car.scth.mvp.ui.activity.MileageStatisticsActivity;
import com.car.scth.mvp.ui.activity.OilElectricityControlActivity;
import com.car.scth.mvp.ui.activity.OperationRecordActivity;
import com.car.scth.mvp.ui.activity.RealTimeModeActivity;
import com.car.scth.mvp.ui.activity.RealTimeTrackAmapActivity;
import com.car.scth.mvp.ui.activity.RealTimeTrackBaiduActivity;
import com.car.scth.mvp.ui.activity.RecordActivity;
import com.car.scth.mvp.ui.activity.RemoteListeningActivity;
import com.car.scth.mvp.ui.activity.RemoteSwitchActivity;
import com.car.scth.mvp.ui.activity.SimDetailActivity;
import com.car.scth.mvp.ui.activity.SysSettingActivityActivity;
import com.car.scth.mvp.ui.activity.TrackAmapActivity;
import com.car.scth.mvp.ui.activity.TrackBaiduActivity;
import com.car.scth.mvp.ui.adapter.DeviceFunctionAdapter;
import com.car.scth.mvp.utils.BroadcastReceiverUtil;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.FunctionType;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.BreakdownExamineDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 设备功能-个人版
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FunctionUserFragment extends BaseFragment<FunctionUserPresenter> implements FunctionUserContract.View {

    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.function_title)
    TextView tvTitleName;

    private List<DeviceFunctionBean> functionBeans;
    private DeviceFunctionAdapter mAdapter;

    private ChangePageReceiver receiver; // 注册广播接收器
    private String mSimei = ""; // 选中设备的simei号
    private String modeFunction; // 支持的模式，e_mode_invalid 什么模式都不支持，e_mode_loc 支持多选的模式，e_mode_rtls 支持定时的模式
    private int remoteSwitch; // 是否可远程开关机, -1不支持,1支持,0支持，但是不可用
    private int onTimeSwitch; // 是否有定时开关机功能, 0不支持,1支持
    private List<String> cmdRead; // 支持的指令相关,但是不可用，支持但不可使用
    private int remoteListen; // 是否远程听音, 0不支持,1支持

    private final static int Intent_Check_Icon = 10; // 切换图标

    public static FunctionUserFragment newInstance() {
        FunctionUserFragment fragment = new FunctionUserFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFunctionUserComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_function_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        functionBeans = new ArrayList<>();
        cmdRead = new ArrayList<>();
        mAdapter = new DeviceFunctionAdapter(getContext(), R.layout.item_device_function, functionBeans);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    if(TextUtils.isEmpty(MyApplication.getMyApp().getImei() + "") || MyApplication.getMyApp().getImei() == 0){
                        ToastUtils.show(getString(R.string.txt_device_select_location));
                        return;
                    }
                    onFunctionClick(functionBeans.get(position).getId());
                }
            }
        });

        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("function");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(receiver, filter);
    }

    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String carName = MyApplication.getMyApp().getCarName();
            String imei = String.valueOf(MyApplication.getMyApp().getImei());
            String simei = MyApplication.getMyApp().getSimei();
            if (ConstantValue.isLoginForAccount()){
                if (TextUtils.isEmpty(simei)){
                    mSimei = "";
                    onAddFunctionData();
                    tvTitleName.setText(getString(R.string.txt_function));
                }else{
                    setTitleText(carName, imei);
                    mSimei = simei;
                    getDeviceConfig();
                }
            }else{
                setTitleText(carName, imei);
                mSimei = simei;
                getDeviceConfig();
            }
        }
    }

    private void setTitleText(String carName, String imei) {
        if (tvTitleName != null) {
            if (TextUtils.isEmpty(carName)) {
                if (MyApplication.getMyApp().getImei() != 0)
                    tvTitleName.setText(imei);
            } else {
                tvTitleName.setText(carName);
            }
        }
    }

    /**
     * 获取设备的配置信息，支持的功能等
     */
    private void getDeviceConfig() {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        if (!TextUtils.isEmpty(mSimei)){
            paramsBean.setSimei(mSimei);
        }
        paramsBean.setType(ResultDataUtils.Config_Other);

        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);

        getPresenter().getDeviceConfig(bean);
    }

    /**
     * 添加功能数据
     */
    private void onAddFunctionData(){
        cmdRead.clear();
        functionBeans.clear();
        if (getContext() != null) {
            functionBeans.add(new DeviceFunctionBean(1, R.mipmap.icon_luyin, getString(R.string.txt_function_record)));
            functionBeans.add(new DeviceFunctionBean(2, R.mipmap.icon_caozuo, getString(R.string.txt_operation_record)));
            functionBeans.add(new DeviceFunctionBean(3, R.mipmap.icon_baojing, getString(R.string.txt_alert_msg)));
            functionBeans.add(new DeviceFunctionBean(4, R.mipmap.icon_guiji, getString(R.string.txt_history_track)));
            functionBeans.add(new DeviceFunctionBean(5, R.mipmap.icon_dingwei, getString(R.string.txt_function_location_mode)));
            functionBeans.add(new DeviceFunctionBean(6, R.mipmap.icon_yuancheng, getString(R.string.txt_function_remote_switch)));
            functionBeans.add(new DeviceFunctionBean(14, R.mipmap.icon_zhuizong, getString(R.string.txt_function_realtime_tracking)));
            functionBeans.add(new DeviceFunctionBean(7, R.mipmap.icon_weilan, getString(R.string.txt_function_fence)));
            functionBeans.add(new DeviceFunctionBean(9, R.mipmap.icon_licheng, getString(R.string.txt_function_mileage_statistics)));
            functionBeans.add(new DeviceFunctionBean(10, R.mipmap.icon_yijianchongqi, getString(R.string.txt_function_one_click_restart)));
            functionBeans.add(new DeviceFunctionBean(11, R.mipmap.icon_set, getString(R.string.txt_sys_set)));
            functionBeans.add(new DeviceFunctionBean(15, R.mipmap.icon_yuodiankongzhi, getString(R.string.txt_function_oil_and_electricity_control)));
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 功能点击事件
     */
    private void onFunctionClick(int id){
        switch (id){
            case 0:
                launchActivity(SimDetailActivity.newInstance());
                break;
            case 1:
                launchActivity(RecordActivity.newInstance());
                break;
            case 2:
                launchActivity(OperationRecordActivity.newInstance());
                break;
            case 3:
                launchActivity(AlarmRecordActivity.newInstance());
                break;
            case 4:
                if (ResultDataUtils.Device_Mode_Call_One.equals(MyApplication.getMyApp().getDeviceMode())
                        || ResultDataUtils.Device_Mode_Sup_Save_Power_C2.equals(MyApplication.getMyApp().getDeviceMode())) {
                    AlertBean bean = new AlertBean();
                    bean.setTitle(getString(R.string.txt_tip));
                    bean.setAlert(getString(R.string.txt_quest_track_hint));
                    bean.setType(0);
                    AlertAppDialog dialog = new AlertAppDialog();
                    dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                        @Override
                        public void onConfirm() {
                            onLookDeviceHistoryTrack();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                } else {
                    onLookDeviceHistoryTrack();
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(modeFunction)){
                    if (modeFunction.equals(ResultDataUtils.Mode_Stand_By_Location)){
                        launchActivity(LocationModeActivity.newInstance());
                    }else{
                        launchActivity(RealTimeModeActivity.newInstance());
                    }
                }
                break;
            case 6:
                launchActivity(RemoteSwitchActivity.newInstance(remoteSwitch, onTimeSwitch));
                break;
            case 7:
                launchActivity(FenceActivity.newInstance());
                break;
            case 9:
                launchActivity(MileageStatisticsActivity.newInstance());
                break;
            case 10:
                onConfirmOneKeyFunction(ResultDataUtils.Function_Restart);
                break;
            case 11:
                launchActivity(SysSettingActivityActivity.newInstance(remoteListen));
                break;
            case 14:
                isDeviceRealTimeTrack();
                break;
            case 15:
                launchActivity(OilElectricityControlActivity.newInstance());
                break;

            case 12:
//                if (!onOperationCanUse(ResultDataUtils.Function_Restart)){
//                    return;
//                }
//                onConfirmOneKeyFunction(ResultDataUtils.Function_Restart);
                break;
            case 13:
                if (isStateLineDown()){
                    return;
                }
//                if (!onOperationCanUse(ResultDataUtils.Function_Finddev)){
//                    return;
//                }
                onConfirmOneKeyFunction(ResultDataUtils.Function_Finddev);
                break;
            case 17:
//                if (!onOperationCanUse(ResultDataUtils.Function_Wakeup)){
//                    return;
//                }
                if (!TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
                        && MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)){
                    ToastUtils.show(getString(R.string.txt_operation_can_not));
                    return;
                }
                onConfirmOneKeyFunction(ResultDataUtils.Function_Wakeup);
                break;
//            case 18:
//                Intent intent = new Intent(getContext(), IconCheckActivity.class);
//                startActivityForResult(intent, Intent_Check_Icon);
//                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Intent_Check_Icon){
                BroadcastReceiverUtil.showMainPage(getContext(), 0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设备是否处于离线状态
     * @return
     */
    private boolean isStateLineDown(){
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
                && MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)){
            ToastUtils.show(getString(R.string.txt_state_line_down_hint));
            return true;
        }else{
            return false;
        }
    }

    /**
     * 是否允许查看实时追踪
     * @return
     */
    private void isDeviceRealTimeTrack(){
        if (MyApplication.getMyApp().getVersion().toUpperCase().equals(FunctionType.C2)){
            if (MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)){
                ToastUtils.show(getString(R.string.txt_real_time_trace_off_line_hint));
                return;
            }
            if (MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Sleep)){
                ToastUtils.show(getString(R.string.txt_real_time_trace_sleep_hint));
                return;
            }
        }

        if (MyApplication.getMyApp().getVersion().toUpperCase().equals(FunctionType.C2)){
            AlertBean bean = new AlertBean();
            bean.setTitle(getString(R.string.txt_tip));
            bean.setAlert(getString(R.string.txt_real_time_trace_hint));
            bean.setType(0);
            AlertAppDialog dialog = new AlertAppDialog();
            dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                @Override
                public void onConfirm() {
                    onLookDeviceRealTimeTrack();
                }

                @Override
                public void onCancel() {

                }
            });
        }else{
            onLookDeviceRealTimeTrack();
        }
    }

    /**
     * 查看实时追踪
     */
    private void onLookDeviceRealTimeTrack(){
        switch (ConstantValue.getMapType()){
            case 0:
                launchActivity(RealTimeTrackAmapActivity.newInstance());
                break;
            case 1:
                launchActivity(RealTimeTrackBaiduActivity.newInstance());
                break;
            case 2:
                break;
        }
    }

    /**
     * 查看轨迹回放
     */
    private void onLookDeviceHistoryTrack(){
        switch (ConstantValue.getMapType()){
            case 0:
                launchActivity(TrackAmapActivity.newInstance());
                break;
            case 1:
                launchActivity(TrackBaiduActivity.newInstance());
                break;
            case 2:
                break;
        }
    }

    /**
     * 操作是否可操作
     * @param cmd 操作的指令
     */
    private boolean onOperationCanUse(String cmd){
        if (cmdRead.size() > 0 && cmdRead.contains(cmd)){
            ToastUtils.show(getString(R.string.txt_operation_can_not));
            return false;
        }else{
            return true;
        }
    }

    /**
     * 一键功能提交
     * @param key 功能值
     */
    private void onConfirmOneKeyFunction(String key){
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        switch (key){
            case ResultDataUtils.Function_Restart:
                bean.setAlert(getString(R.string.txt_restart_hint));
                break;
            case ResultDataUtils.Function_Sleep:
                bean.setAlert(getString(R.string.txt_one_key_sleep));
                break;
            case ResultDataUtils.Function_Finddev:
                bean.setAlert(getString(R.string.txt_looking_for_equipment));
                break;
            case ResultDataUtils.Function_Wakeup:
                bean.setAlert(getString(R.string.txt_one_key_wakeup));
                break;
            case ResultDataUtils.Function_Reset:
                bean.setAlert(getString(R.string.txt_reset_device_hint));
                break;
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitOneKeyFunction(key);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 一键功能提交
     * @param key 功能值
     */
    private void submitOneKeyFunction(String key){
        OnKeyFunctionPutBean.ParamsBean paramsBean = new OnKeyFunctionPutBean.ParamsBean();
        paramsBean.setType(key);
        paramsBean.setSimei(mSimei);

        OnKeyFunctionPutBean bean = new OnKeyFunctionPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_OnKey_Function);
        bean.setModule(ModuleValueService.Module_For_OnKey_Function);

        showProgressDialog();

        getPresenter().submitOneKeyFunction(bean);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某�?Fragment 对象执行一些方�?
     * 建议在有多个需要与外界交互的方法时, 统一�?{@link Message}, 通过 what 字段来区分不同的方法, �?{@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操�? 可以起到分发的作�?     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周�? 如果调用 {@link #setData(Object)} 方法�?{@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用�?Presenter 的方�? 是会报空�? 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调�?{@link #setData(Object)}, �?{@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以�?{@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        modeFunction = "";
        modeFunction = deviceConfigResultBean.getMode_fun();
        remoteSwitch = deviceConfigResultBean.getRemoteswitch();
        onTimeSwitch = deviceConfigResultBean.getOntime_switch();
        remoteListen = deviceConfigResultBean.getMonitor();
        cmdRead.clear();
        if (deviceConfigResultBean.getCmd_read() != null){
            cmdRead.addAll(deviceConfigResultBean.getCmd_read());
        }
        functionBeans.clear();
        if (deviceConfigResultBean.getRecord() == 1){
            functionBeans.add(new DeviceFunctionBean(1, R.mipmap.icon_luyin, getString(R.string.txt_function_record)));
        }
        functionBeans.add(new DeviceFunctionBean(2, R.mipmap.icon_caozuo, getString(R.string.txt_operation_record)));//操作记录
        functionBeans.add(new DeviceFunctionBean(3, R.mipmap.icon_baojing, getString(R.string.txt_alert_msg)));//报警
        functionBeans.add(new DeviceFunctionBean(4, R.mipmap.icon_guiji, getString(R.string.txt_history_track)));//轨迹回放

        if (!modeFunction.equals(ResultDataUtils.Mode_Stand_By_Invalid)){
            functionBeans.add(new DeviceFunctionBean(5, R.mipmap.icon_dingwei, getString(R.string.txt_function_location_mode)));
        }

        if (remoteSwitch != -1 || onTimeSwitch == 1){
            functionBeans.add(new DeviceFunctionBean(6, R.mipmap.icon_yuancheng, getString(R.string.txt_function_remote_switch)));
        }
        if (deviceConfigResultBean.getTrace() == 1){
            functionBeans.add(new DeviceFunctionBean(14, R.mipmap.icon_zhuizong, getString(R.string.txt_function_realtime_tracking)));
        }
        functionBeans.add(new DeviceFunctionBean(7, R.mipmap.icon_weilan, getString(R.string.txt_function_fence)));
        functionBeans.add(new DeviceFunctionBean(9, R.mipmap.icon_licheng, getString(R.string.txt_function_mileage_statistics)));
        for (String string : deviceConfigResultBean.getCmd_type()){
            if (string.equals(ResultDataUtils.Function_Restart)){
                functionBeans.add(new DeviceFunctionBean(10, R.mipmap.icon_yijianchongqi, getString(R.string.txt_function_one_click_restart)));
            }
        }
        functionBeans.add(new DeviceFunctionBean(11, R.mipmap.icon_set, getString(R.string.txt_sys_set)));

        if (deviceConfigResultBean.getConfig().getCar_switch().getReplaystate() != -1){
            functionBeans.add(new DeviceFunctionBean(15, R.mipmap.icon_yuodiankongzhi, getString(R.string.txt_function_oil_and_electricity_control)));
        }


//        functionBeans.add(new DeviceFunctionBean(18, R.drawable.icon_switch_device_icon, getString(R.string.txt_switch_device_icon)));


//        if (deviceConfigResultBean.getMonitor() == 1){
//            functionBeans.add(new DeviceFunctionBean(9, R.drawable.icon_function_remote_listening, getString(R.string.txt_function_remote_listening)));
//        }

//        for (String string : deviceConfigResultBean.getCmd_type()){
//            switch (string){
//                case ResultDataUtils.Function_Sleep:
//                    if (!TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
//                            && MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_On)){
//                        functionBeans.add(new DeviceFunctionBean(13, R.drawable.icon_function_one_key_sleep, getString(R.string.txt_function_one_key_sleep)));
//                    }
//                    break;
//                case ResultDataUtils.Function_Wakeup:
//                    if (!TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
//                            && !MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_On)){
//                        functionBeans.add(new DeviceFunctionBean(17, R.drawable.icon_function_one_key_sleep, getString(R.string.txt_function_one_key_wakeup)));
//                    }
//                    break;
//            }
//        }
        for (String string : deviceConfigResultBean.getCmd_type()){
            if (string.equals(ResultDataUtils.Function_Finddev)){
                functionBeans.add(new DeviceFunctionBean(13, R.mipmap.icon_search, getString(R.string.txt_function_looking_device)));
            }
        }

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void submitOneKeyFunctionSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onResume() {
        MobclickAgent.onPageStart("Function");//统计页面("MainScreen"为页面名称，可自定义)
        super.onResume();
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("Function");
        super.onPause();
    }

}
