package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AlertCurrentBean;
import com.car.scth.mvp.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 通用提示弹窗，输入文本
 */
public class AlarmCurrentEditDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvTitle; // 标题
    private TextView tvTitleSmall; // 副标题
    private EditText edtValue; // 输入的文本
    private Button btnCancel; // 取消
    private Button btnConfirm; // 确定

    private AlertCurrentBean mBean;
    private onAlartCurrentChange currentChange;
    private boolean isDismiss = true; //是否 dismiss  默认是
    private boolean allowEmpty; //是否允许为空
    private int maxLength; //最大字符

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_current_alert, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(false);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            int width = ScreenUtils.getScreenWidth() * 3 / 4;
            params.width = width;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvTitle = dialog.findViewById(R.id.tv_alert_title);
        tvTitleSmall = dialog.findViewById(R.id.tv_alert_small);
        edtValue = dialog.findViewById(R.id.edt_value);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        tvTitle.setText(mBean.getTitle());
        if (!TextUtils.isEmpty(mBean.getTitleSmall())) {
            tvTitleSmall.setVisibility(View.VISIBLE);
            tvTitleSmall.setText(mBean.getTitleSmall());
        } else {
            tvTitleSmall.setVisibility(View.GONE);
        }
        if (mBean.isTextPassword()) {
            edtValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //输入类型为普通文本密码
        } else {
            if (mBean.getEditType() == InputType.TYPE_CLASS_NUMBER)
                edtValue.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型为数字
            else
                edtValue.setInputType(InputType.TYPE_CLASS_TEXT); //输入类型为普通文本
        }
        edtValue.setHint(mBean.getHint());
        if (!TextUtils.isEmpty(mBean.getEditValue())) {
            edtValue.setText(mBean.getEditValue());
            edtValue.setSelection(mBean.getEditValue().length());
        }
        // 设置过滤器，限制只能输入汉字,英文，数字
        edtValue.setFilters(new InputFilter[]{new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_].");

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher matcher = pattern.matcher(source);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.show(getString(R.string.txt_edt_prompt));
                    return "";
                }
            }
        }});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                hideKeyboard(edtValue);
                if (currentChange != null) {
                    currentChange.onEditCancel();
                }
                dismiss();
                break;
            case R.id.btn_confirm:
                hideKeyboard(edtValue);
                onConfirm();
                break;
        }
    }

    /**
     * 收起软键盘
     *
     * @param view
     */
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void onConfirm() {
        String value = edtValue.getText().toString().trim();
        if (TextUtils.isEmpty(value) && !allowEmpty) {
            ToastUtils.show(mBean.getHint());
            return;
        }

        if (currentChange != null) {
            currentChange.onEditConfirm(value);
        }
        if (isDismiss)
            dismiss();
    }

    public void setDialogDismiss(boolean value) {
        isDismiss = value;
    }

    public void setAllowEmpty(boolean value) {
        allowEmpty = value;
    }

    public void setMaxLength(int value) {
        maxLength = value;
    }

    public void show(FragmentManager manager, AlertCurrentBean bean, onAlartCurrentChange change) {
        if (isAdded()) {
            dismiss();
        }
        this.mBean = bean;
        this.currentChange = change;
        super.show(manager, "AlarmCurrentEditDialog");
    }

    public interface onAlartCurrentChange {

        /**
         * 输入完成
         *
         * @param textValue
         */
        void onEditConfirm(String textValue);

        /**
         * 取消输入
         */
        void onEditCancel();

    }

}
