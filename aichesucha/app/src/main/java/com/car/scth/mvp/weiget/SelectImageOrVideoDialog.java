package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.car.scth.R;

/**
 * 选择图片或者视频
 */
public class SelectImageOrVideoDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvDismiss;
    private TextView tvImage;
    private TextView tvVideo;
    private onSelectImageVideoChange imageVideoChange;
    private int selectType = 0; // 0:取消，1：上传图片，2：上传视频

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_select_image_video, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog){
        try{
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        tvImage = dialog.findViewById(R.id.tv_image);
        tvVideo = dialog.findViewById(R.id.tv_video);
        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        tvImage.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
        tvDismiss.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_image:
                selectType = 1;
                if (imageVideoChange != null){
                    imageVideoChange.onSelectImageVideo(selectType);
                }
                dismiss();
                break;
            case R.id.tv_video:
                selectType = 2;
                if (imageVideoChange != null){
                    imageVideoChange.onSelectImageVideo(selectType);
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                selectType = 0;
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (selectType == 0){
            if (imageVideoChange != null){
                imageVideoChange.onSelectDismiss();
            }
        }
        super.onDestroy();
    }

    public void show(FragmentManager manager, onSelectImageVideoChange change){
        if (isAdded()){
            dismiss();
        }
        this.imageVideoChange = change;
        super.show(manager, "SelectImageOrVideoDialog");
    }

    public interface onSelectImageVideoChange{

        /**
         * 选择了上传方式
         * @param id 1:上传图片，2：上传视频
         */
        void onSelectImageVideo(int id);

        /**
         * 选择取消
         */
        void onSelectDismiss();

    }

}
