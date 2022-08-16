package com.car.scth.mvp.weiget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import com.car.scth.R;

/**
 * 合并账号
 */
public class MergeAccountDialog extends DialogFragment implements View.OnClickListener {

    private EditText edtCode;
    private TextView tvSendCode;
    private Button btnCancel;
    private Button btnMerge;
    private onMergeAccountChange accountChange;
    private Disposable disposable; // 验证码倒计时

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_merge_account, null);
        dialog.setContentView(viewGroup);
        setCancelable(false); // 点击屏幕或物理返回键，true：dialog消失，false：不消失
        dialog.setCanceledOnTouchOutside(false); // 点击屏幕，dialog不消失；点击物理返回键dialog消失
        initView(dialog);
        return dialog;
    }

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

        edtCode = dialog.findViewById(R.id.edt_code);
        tvSendCode = dialog.findViewById(R.id.tv_send_code);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnMerge = dialog.findViewById(R.id.btn_merge);
        tvSendCode.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnMerge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send_code:
                onSendCode();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_merge:
                onMergeAccount();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void onSendCode(){
        if (accountChange != null){
            accountChange.onSendCode();
        }
        countDownTime();
    }

    /**
     * 合并账号
     */
    private void onMergeAccount(){
        String code = edtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)){
            ToastUtils.showShort(getString(R.string.txt_input_code));
            return;
        }
        if (accountChange != null){
            accountChange.onMergeAccount(code);
        }
    }

    public void show(FragmentManager manager, onMergeAccountChange change){
        if (isAdded()){
            dismiss();
        }
        this.accountChange = change;
        super.show(manager, "MergeAccountDialog");
    }

    /**
     * 获取验证码倒计时
     */
    public void countDownTime() {
        final int count = 60;//倒计时10秒
        //ui线程中进行控件更新
        Observable<Long> countDownObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        tvSendCode.setEnabled(false);
                    }
                });

        //回复原来初始状态
        disposable = countDownObservable.subscribe(new Consumer<Long>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void accept(Long aLong) throws Exception {
                tvSendCode.setText(aLong + "s");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //回复原来初始状态
                tvSendCode.setEnabled(true);
                tvSendCode.setText(getString(R.string.txt_send_code));
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroyView();
    }

    public interface onMergeAccountChange{

        /**
         * 发送验证码
         */
        void onSendCode();

        /**
         * 合并账号
         * @param code 验证码
         */
        void onMergeAccount(String code);
    }

}
