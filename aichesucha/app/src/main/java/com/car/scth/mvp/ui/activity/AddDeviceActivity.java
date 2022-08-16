package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerAddDeviceComponent;
import com.car.scth.mvp.contract.AddDeviceContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AddDeviceInfoBean;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.putbean.AddDevicePutBean;
import com.car.scth.mvp.presenter.AddDevicePresenter;
import com.car.scth.mvp.ui.adapter.AddDeviceInfoAdapter;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.DeviceFailDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2021 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddDeviceActivity extends BaseActivity<AddDevicePresenter> implements AddDeviceContract.View {

    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.edt_imei)
    EditText edtImei;
    @BindView(R.id.iv_sqr)
    ImageView ivSqr;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_paste)
    TextView tvPaste;
    @BindView(R.id.tv_sqr_number)
    TextView tvSqrNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mSid; // 分组id
    private List<AddDeviceInfoBean> infoBeans;
    private AddDeviceInfoAdapter mAdapter;
    private static final int INTENT_SCAN_QR = 10; // 扫描二维码/条形码回调
    private static final String KAY_Sid = "kay_sid";

    private List<AddDevicePutBean.ParamsBean.InfoBean> submitBeans; // 上传的数据

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddDeviceComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_device;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_add_device));
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_save));
        infoBeans = new ArrayList<>();
        submitBeans = new ArrayList<>();
        mSid = getIntent().getStringExtra(KAY_Sid);

//        edtImei.setFilters(new InputFilter[]{mInputFilter});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddDeviceInfoAdapter(R.layout.item_add_device_info, infoBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_delete_info));
                bean.setType(0);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onConfirm() {
                        infoBeans.remove(position);
                        mAdapter.notifyDataSetChanged();
                        if (infoBeans.size() > 0){
                            tvSqrNumber.setText(getString(R.string.txt_sqr_number) + infoBeans.size() + " " + getString(R.string.txt_delete_hint));
                        }else{
                            tvSqrNumber.setText(getString(R.string.txt_sqr_number));
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                return false;
            }
        });

    }

    private InputFilter mInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String editContent = source.toString();
            editContent = editContent.replace("\n", "");
            editContent = editContent.replace("\r", "");
            editContent = editContent.replace(" ", "");
            return editContent;
        }

    };

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
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_right, R.id.iv_sqr, R.id.tv_clear, R.id.tv_paste})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            switch (view.getId()) {
                case R.id.toolbar_right: // 保存
                    onAddDeviceSave();
                    break;
                case R.id.iv_sqr: // 扫码二维码
                    Intent intent_qr = new Intent(this, HMSScanQrCodeActivity.class);
                    intent_qr.putExtra("type", 2);
                    startActivityForResult(intent_qr, INTENT_SCAN_QR);
                    break;
                case R.id.tv_clear: // 清空
                    hideKeyboard(edtImei);
                    edtImei.setText("");
                    break;
                case R.id.tv_paste: // 智能填写
                    onCopyPasteDevice();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_SCAN_QR) {
                String imei = data.getStringExtra("imei");
                if (!TextUtils.isEmpty(imei)) {
                    setDataForScanQrCode(imei);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 解析扫码数据
     * @param imeis
     */
    @SuppressLint("SetTextI18n")
    private void setDataForScanQrCode(String imeis){
        boolean isHasDevice = false; // 是否已经包含了这个设备
        if (imeis.contains(",")){
            String[] mImeis = imeis.split(",");
            for (int i = 0; i < mImeis.length; i++){
                isHasDevice = false;
                for (AddDeviceInfoBean infoBean : infoBeans){
                    if (mImeis[i].equals(infoBean.getImei())){
                        isHasDevice = true;
                        break;
                    }
                }
                if (!isHasDevice){
                    AddDeviceInfoBean bean = new AddDeviceInfoBean();
                    bean.setImei(mImeis[i]);
                    infoBeans.add(bean);
                }
            }
        }else{
            for (AddDeviceInfoBean infoBean : infoBeans){
                if (imeis.equals(infoBean.getImei())){
                    isHasDevice = true;
                    break;
                }
            }
            if (!isHasDevice){
                AddDeviceInfoBean bean = new AddDeviceInfoBean();
                bean.setImei(imeis);
                infoBeans.add(bean);
            }
        }
        mAdapter.notifyDataSetChanged();
        tvSqrNumber.setText(getString(R.string.txt_sqr_number) + infoBeans.size() + " " + getString(R.string.txt_delete_hint));
    }

    /**
     * 提交保存
     */
    private void onAddDeviceSave(){
        if (infoBeans.size() == 0){
            ToastUtils.show(getString(R.string.txt_device_add_hint));
            return;
        }

        submitBeans.clear();
        for (int i = 0; i < infoBeans.size(); i++){
            if (!TextUtils.isEmpty(infoBeans.get(i).getImei())){
                if (infoBeans.get(i).getImei().length() >= 19) {
                    com.blankj.utilcode.util.ToastUtils.showShort(infoBeans.get(i).getImei() + "  " + getString(R.string.txt_imei_length_error));
                    continue;
                }
                AddDevicePutBean.ParamsBean.InfoBean bean = new AddDevicePutBean.ParamsBean.InfoBean();
                bean.setImei(Long.parseLong(infoBeans.get(i).getImei()));
                submitBeans.add(bean);
            }
        }
        AddDevicePutBean.ParamsBean paramsBean = new AddDevicePutBean.ParamsBean();
        paramsBean.setInfo(submitBeans);
        paramsBean.setSgid(mSid);

        AddDevicePutBean bean = new AddDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Add_Device);
        bean.setModule(ModuleValueService.Module_For_Add_Device);

        if (getPresenter() != null){
            getPresenter().submitAddDevice(bean);
        }
    }

    /**
     * 智能填写设备号码
     */
    @SuppressLint("SetTextI18n")
    private void onCopyPasteDevice() {
        String edtDevice = edtImei.getText().toString().trim();
        if (TextUtils.isEmpty(edtDevice)) {
            ToastUtils.show(getString(R.string.txt_edit_or_paste_device));
            return;
        }
        // 开始循环数据
        String[] deviceList;
        if (edtDevice.contains("\n")){
            deviceList = edtDevice.split("\n");
        }else if (edtDevice.contains("\r")){
            deviceList = edtDevice.split("\r");
        }else{
            deviceList = edtDevice.split("@");
        }
        for (int i = 0; i < deviceList.length; i++) {
            String device = deviceList[i];
            String[] imeis = device.split("\\+");
            AddDeviceInfoBean bean = new AddDeviceInfoBean();
            for (int h = 0; h < imeis.length; h++) {
                switch (h) {
                    case 0:
                        // 判断是否为全数字编号
                        String imei = imeis[h].trim();
                        imei = imei.replace("\n", "");
                        imei = imei.replace("\r", "");
                        imei = imei.replaceAll(" ", "");
                        if (imei.matches("^[0-9]*$")) {
                            bean.setImei(imei);
                        } else {
                            ToastUtils.show(getString(R.string.txt_device_imei_error));
                            break;
                        }
                        break;
                    case 1:
                        bean.setRemark(imeis[h]);
                        break;
                    case 2:
                        bean.setName(imeis[h]);
                        break;
                    case 3:
                        bean.setMobile(imeis[h]);
                        break;
                }
            }
            if (!TextUtils.isEmpty(bean.getImei())){
                boolean isHasDevice = false; // 是否已经包含了这个设备
                for (AddDeviceInfoBean infoBean : infoBeans){
                    if (bean.getImei().equals(infoBean.getImei())){
                        isHasDevice = true;
                        break;
                    }
                }
                if (!isHasDevice){
                    infoBeans.add(bean);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        tvSqrNumber.setText(getString(R.string.txt_sqr_number) + infoBeans.size() + " " + getString(R.string.txt_delete_hint));
    }

    @Override
    public void submitAddDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0){
            DeviceFailDialog dialog = new DeviceFailDialog();
            dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 0);
            if (submitBeans.size() > deviceBaseResultBean.getFail_items().size()){
                ToastUtils.show(getString(R.string.txt_add_success));
            }
        }else{
            ToastUtils.show(getString(R.string.txt_add_success));
        }
    }
}
