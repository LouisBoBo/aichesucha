package com.car.scth.mvp.weiget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.utils.DownloadUtil;
import com.car.scth.mvp.utils.FileUtilApp;
import com.car.scth.mvp.utils.ResultDataUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;

/**
 * 版本更新提示
 * Created by Administrator on 2019\6\21 0021.
 */

public class UploadAppDialog extends DialogFragment {

    private TextView mTvVersion; // 提示
    private TextView mTvAlert; // 提示语
    private Button mBtnCancel; // 取消
    private Button mBtnConfirm; // 确定

    private CheckAppUpdateBean updateBean;
    private onAlertDialogChange alertDialogChange;
    private int mUploadProgress = 0; // 下载进度

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_app_upload, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(false);
        setCancelable(false); // 点击屏幕或物理返回键，true：dialog消失，false：不消失
        initView(dialog);
        return dialog;
    }

    @SuppressLint("SetTextI18n")
    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ScreenUtils.getScreenWidth() * 3 / 4;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTvAlert = dialog.findViewById(R.id.tv_alert);
        mBtnCancel = dialog.findViewById(R.id.btn_cancel);
        mBtnConfirm = dialog.findViewById(R.id.btn_confirm);
        mTvVersion = dialog.findViewById(R.id.tv_version_name);

        mTvVersion.setText("V" + updateBean.getName());
        mTvAlert.setText(updateBean.getInfo());

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadUtil.getInstance().downLoadCancel();
                if (alertDialogChange != null){
                    alertDialogChange.onCancel();
                }
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateBean.getType().equals(ResultDataUtils.App_Update_Interior)){
                    onAppDownLoadUrl();
                }else{
                    if (alertDialogChange != null){
                        alertDialogChange.onThreeDownLoad();
                    }
                }
            }
        });

    }

    /**
     * app下载apk
     */
    private void onAppDownLoadUrl(){
        if (updateBean.getUrl().startsWith("http") || updateBean.getUrl().startsWith("https")){
            mBtnConfirm.setText(getString(R.string.txt_soft_updating));
            mBtnConfirm.setEnabled(false);
            //储存下载文件的目录
            try{
                DownloadUtil.getInstance().download(updateBean.getUrl(),
                        FileUtilApp.getSDPath(MyApplication.getMyApp()) + FileUtilApp.FileDownLoad,
                        new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(String path) {
                        if (isAdded()){
//                            ToastUtil.showShort("安装包已保存到" + FileUtil.FileRecordName);
                            mBtnConfirm.setEnabled(true);
                            if (alertDialogChange != null){
                                alertDialogChange.onAppDownLoad(path);
                            }
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDownloading(int progress) {
                        if (isAdded()){
                            if (progress >= mUploadProgress){
                                mUploadProgress = progress;
                            }
                            mBtnConfirm.setText(getString(R.string.txt_soft_updating) + "：" + mUploadProgress + "%");
                            if (mUploadProgress == 100){
                                mBtnConfirm.setText(getString(R.string.txt_soft_updating_success));
                            }
                        }
                    }

                    @Override
                    public void onDownloadFailed() {
                        if (isAdded()){
                            ToastUtils.showShort(getString(R.string.txt_download_falied));
                            mBtnConfirm.setEnabled(true);
                            mBtnConfirm.setText(getString(R.string.txt_update_app));
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ToastUtils.showShort(getString(R.string.txt_upload_url_error));
        }
    }

    public void show(FragmentManager manager, CheckAppUpdateBean bean, onAlertDialogChange change){
        if (isAdded()){
            dismiss();
        }
        this.updateBean = bean;
        this.alertDialogChange = change;
        showAllowingStateLoss(manager, "UploadAppDialog");
    }

    public void showAllowingStateLoss(FragmentManager manager, String tag){
        try {
            Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            shown.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }


    public interface onAlertDialogChange{

        /**
         * app下载
         */
        void onAppDownLoad(String path);

        /**
         * 第三方下载
         */
        void onThreeDownLoad();

        /**
         * 取消
         */
        void onCancel();

    }

    @Override
    public void onDestroy() {
        DownloadUtil.getInstance().downLoadCancel();
        super.onDestroy();
    }
}
