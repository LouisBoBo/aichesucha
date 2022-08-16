package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.ml.scan.HmsScan;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerHMSScanQrCodeComponent;
import com.car.scth.mvp.contract.HMSScanQrCodeContract;
import com.car.scth.mvp.presenter.HMSScanQrCodePresenter;
import com.car.scth.mvp.ui.adapter.ScanQrCodeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2021 14:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HMSScanQrCodeActivity extends BaseActivity<HMSScanQrCodePresenter> implements HMSScanQrCodeContract.View {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.back_img)
    ImageView ivBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.listView)
    ListView listView;

    private RemoteView remoteView;

    int mScreenWidth;
    int mScreenHeight;
    //The width and height of scan_view_finder is both 240 dp.
    final int SCAN_FRAME_SIZE = 240;
    private static final String TAG = "DefinedActivity";
    //Declare the key. It is used to obtain the value returned from Scan Kit.
    public static final String SCAN_RESULT = "scanResult";

    private int mType = 1; // 1:登录扫码，2：添加设备扫码
    private String mResult = ""; // 扫码结果
    private List<String> mImeis; // 添加设备扫码结果
    private ScanQrCodeAdapter mAdapter;
    private boolean isAdd;  // 是否有相同的imei号
    private Vibrator vibrator; // 震动

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHMSScanQrCodeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hms_scan_qr_code;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //1. Obtain the screen density to calculate the viewfinder's rectangle.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final float density = dm.density;
        //2. Obtain the screen size.
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        int scanFrameSize = (int) (SCAN_FRAME_SIZE * density);
        //3. Calculate the viewfinder's rectangle, which in the middle of the layout.
        //Set the scanning area. (Optional. Rect can be null. If no settings are specified, it will be located in the middle of the layout.)
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;

        //Initialize the RemoteView instance, and set callback for the scanning result.
        remoteView = new RemoteView.Builder().setContext(this).setBoundingBox(rect).setFormat(HmsScan.ALL_SCAN_TYPE).build();
        // Subscribe to the scanning result callback event.
        remoteView.setOnResultCallback(new OnResultCallback() {
            @Override
            public void onResult(HmsScan[] result) {
                //Check the result.
                if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
                    setResultStr(result[0].getOriginalValue());
                }
            }
        });
        // Load the customized view to the activity.
        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        frameLayout.addView(remoteView, params);
        // Set the back, photo scanning, and flashlight operations.

        mImeis = new ArrayList<>();
        mType = getIntent().getIntExtra("type", 2);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        mAdapter = new ScanQrCodeAdapter(this, R.layout.item_scan_qr_code, mImeis);
        listView.setAdapter(mAdapter);
        if (mType == 1) {
            listView.setVisibility(View.GONE);
            tvSave.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            tvSave.setVisibility(View.VISIBLE);
        }
    }

    private void setResultStr(String result) {
        if (vibrator != null) vibrator.vibrate(300);
        if (result.matches("^[0-9]*$")) { // 扫描条形码或扫描出来的为全数字编号
            // 扫描争取，处理下一步操作
            if (mType == 1) {
                mResult = result;
            } else {
                if (mImeis.size() >= 100) {
                    ToastUtils.showShort(getString(R.string.txt_scan_qr_code_number_error));
                    return;
                }
                setDataForResult(result);
            }
        } else if (result.startsWith("https://") || result.startsWith("http://")) { // 扫描内容是url地址
            ToastUtils.showShort(getString(R.string.txt_scan_qr_error));
            if (mType == 1) {
                mResult = "";
            }
        } else { // 扫描剩余类型
            ToastUtils.showShort(getString(R.string.txt_scan_qr_error));
            if (mType == 1) {
                mResult = "";
            }
        }
        if (mType == 1) {
            if (!TextUtils.isEmpty(mResult)) {
                Intent intent = new Intent();
                intent.putExtra("imei", mResult);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    /**
     * 添加设备扫码处理结果
     *
     * @param result
     */
    private void setDataForResult(String result) {
        isAdd = false;
        for (String imei : mImeis) {
            if (imei.equals(result)) {
                isAdd = true;
                break;
            }
        }
        if (!isAdd) {
            mImeis.add(0, result);
            mAdapter.notifyDataSetChanged();
            if (TextUtils.isEmpty(mResult)) {
                mResult = mResult + result;
            } else {
                mResult = result + "," + mResult;
            }
        }
    }

    /**
     * 保存
     */
    private void onConfirm() {
        if (TextUtils.isEmpty(mResult)) {
            ToastUtils.showShort(getString(R.string.txt_scan_qr_code_save_error));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("imei", mResult);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_img, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tv_save:
                onConfirm();
                break;
        }
    }

    /**
     * Call the lifecycle management method of the remoteView activity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }
}
