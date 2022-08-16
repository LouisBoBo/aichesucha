package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import io.microshow.rxffmpeg.RxFFmpegCommandList;
import io.microshow.rxffmpeg.RxFFmpegInvoke;
import io.microshow.rxffmpeg.RxFFmpegSubscriber;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerCustomerWebViewComponent;
import com.car.scth.mvp.contract.CustomerWebViewContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.presenter.CustomerWebViewPresenter;

import com.car.scth.R;
import com.car.scth.mvp.utils.FileUtilApp;
import com.car.scth.mvp.utils.ImageUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.SelectImageOrVideoDialog;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/28/2021 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CustomerWebViewActivity extends BaseActivity<CustomerWebViewPresenter> implements CustomerWebViewContract.View {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.progress)
    ProgressBar mProgress; // 加载进度条

    private String mTitle = ""; // 标题
    private String mUrl = "";
    private boolean isSend = false; // 是否发送了imei号
    private String mImei = "";

    private ValueCallback<Uri> mUploadCallBack;// 5.0以下表单的数据信息
    private ValueCallback<Uri[]> mUploadCallBackAboveL;//5.0以上表单的数据信息
    private int selectNun = 1; // 可选择的图片数量
    private int mPictureWidthSize;
    private static final int INTENT_CHOOSE_IMAGE = 10; // 选择图片
    private static final int INTENT_CHOOSE_VIDEO = 11; // 选择视频
    private String filePath; // 上一次选中的图片路径，用于删除上一次选中的图片
    private boolean isWebActivity = true; // 是否是在当前页面
    private boolean isCloseLink = false; // 是否断开了连接

    private static final int mCountDownTime = 180; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长

    // 视频压缩处理
    private MyRxFFmpegSubscriber myRxFFmpegSubscriber;
    private ProgressDialog mProgressDialog;
    private long startTime;//记录开始时间
    private long endTime;//记录结束时间

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCustomerWebViewComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_customer_web_view;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        if (MyApplication.getMyApp().getImei() != 0L)
            mImei = String.valueOf(MyApplication.getMyApp().getImei());
        mPictureWidthSize = ScreenUtils.getScreenWidth() / 3;

       setTitle(mTitle);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //webview中缓存模式
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient() {

            @SuppressLint("NewApi")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mUrl = request.getUrl().toString();
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mUrl = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgress.setVisibility(View.GONE);
                if (url.equals(Api.App_Service_Customer_Test)) {
                    if (!TextUtils.isEmpty(mImei)) {
                        if (!isSend) {
                            llTitle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 向html中注入数据
                                    runOnUiThread(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void run() {
                                            // Java调用----> JavaScript的appsendmsg方法 把数据给JavaScript的appsendmsg方法
//                                        webView.loadUrl("javascript:appsendmsg('" + mImei + "')");
                                            isSend = true;
                                            webView.evaluateJavascript("javascript:appsendmsg('" + mImei + "')",
                                                    new ValueCallback<String>() {
                                                        @Override
                                                        public void onReceiveValue(String value) {
                                                            //此处为 js 返回的结果
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }, 1000);
                        }
                    }
                }
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgress.setVisibility(View.VISIBLE);
                mProgress.setProgress(newProgress);
            }

            // 拦截JS的输入框(原理同方式2)
            // 参数message:代表promt（）的内容（不是url）
            // 参数result:代表输入框的返回值
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            // 拦截JS的警告框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                onShowMessage(message);
                return true;
            }

            // 拦截JS的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                onShowMessage(message);
                return true;
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallBackAboveL = filePathCallback;
                onSelectImageOrVideo();
                return true;
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                mUploadCallBack = valueCallback;
                onSelectImageOrVideo();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {
                mUploadCallBack = valueCallback;
                onSelectImageOrVideo();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                mUploadCallBack = valueCallback;
                onSelectImageOrVideo();
            }

        });

        webView.loadUrl(mUrl);
    }

    /**
     * 拦截提示消息
     *
     * @param message
     */
    private void onShowMessage(String message) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(message);
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 选择图片或者视频
     */
    private void onSelectImageOrVideo(){
        onDeleteImageOrVideo();
        SelectImageOrVideoDialog dialog = new SelectImageOrVideoDialog();
        dialog.show(getSupportFragmentManager(), new SelectImageOrVideoDialog.onSelectImageVideoChange() {
            @Override
            public void onSelectImageVideo(int id) {
                isWebActivity = false;
                if (id == 1){
                    onSelectImage();
                }else{
                    onSelectVideo();
                }
            }

            @Override
            public void onSelectDismiss() {
                clearUploadMessage();
            }
        });
    }

    /**
     * 发起选择图片
     */
    private void onSelectImage() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
                .countable(true)
                .showSingleMediaType(true)
                .maxSelectable(selectNun) // 图片选择的最多数量
                .gridExpectedSize(mPictureWidthSize)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .forResult(INTENT_CHOOSE_IMAGE); // 设置作为标记的请求码
    }

    /**
     * 发起选择视频
     */
    private void onSelectVideo(){
        Matisse.from(this)
                .choose(MimeType.ofVideo()) // 选择 mime 的类型
                .countable(true)
                .showSingleMediaType(true)
                .maxSelectable(1) // 图片选择的最多数量
                .gridExpectedSize(mPictureWidthSize)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
//                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(INTENT_CHOOSE_VIDEO); // 设置作为标记的请求码
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_CHOOSE_IMAGE) {
                if (data != null && Matisse.obtainResult(data).size() > 0) {
                    Uri uri = Matisse.obtainResult(data).get(0);
                    String path = ImageUtils.handleImageOnKitKat(uri, this);
                    onLubanImage(path);
                }else{
                    clearUploadMessage();
                }
            } else if (requestCode == INTENT_CHOOSE_VIDEO) {
                if (data != null && Matisse.obtainResult(data).size() > 0) {
                    Uri uri = Matisse.obtainResult(data).get(0);
                    String videoPath = ImageUtils.handleImageOnKitKat(uri, this);
                    if (videoPath.contains(".mp4")) {
                        filePath = FileUtilApp.createFileName(CustomerWebViewActivity.this,FileUtilApp.FileRecordName) + System.currentTimeMillis() + ".mp4";
                        runFFmpegRxJava(videoPath);
                    }else{
                        onUploadVideo(uri);
                    }
                }else{
                    clearUploadMessage();
                }
            }
        }else if (resultCode == Activity.RESULT_CANCELED){
            clearUploadMessage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 对图片进行压缩
     */
    private void onLubanImage(String file){
        Luban.with(this).load(new File(file))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(File file) {
                        Uri uri = ImageUtils.getMediaUriFromPath(CustomerWebViewActivity.this, file.getAbsolutePath());
                        filePath = ImageUtils.handleImageOnKitKat(uri, CustomerWebViewActivity.this);
//                        Log.e("modifyhead", "onSuccess: " + file.getAbsolutePath() + ", uri=" + uri.toString() + ", imagePath=" + imagePath);
                        onUploadImage(uri);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(getString(R.string.txt_luban_error));
                    }
                }).launch();
    }

    /**
     * 开始上传图片
     * @param uri
     */
    private void onUploadImage(Uri uri){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadCallBackAboveL != null) {
                if (uri != null) {
                    mUploadCallBackAboveL.onReceiveValue(new Uri[]{uri});
                    mUploadCallBackAboveL = null;
                }
            }
        }else{
            if (mUploadCallBack != null) {
                if (uri != null) {
                    mUploadCallBack.onReceiveValue(uri);
                    mUploadCallBack = null;
                }
            }
        }
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
    protected void onResume() {
        //激活WebView为活跃状态，能正常执行网页的响应
        webView.onResume();
        //恢复pauseTimers状态
        webView.resumeTimers();
        if (isWebActivity && isCloseLink && Api.App_Service_Customer_Test.equals(mUrl)){
            webView.loadUrl(mUrl);
        }else{
            handler.removeCallbacksAndMessages(null);
        }
        isCloseLink = false;
        isWebActivity = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        webView.onPause();
        //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        webView.pauseTimers();
        if (isWebActivity && Api.App_Service_Customer_Test.equals(mUrl)){
            mCountDown = mCountDownTime;
            handler.sendEmptyMessage(1);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onDeleteImageOrVideo();
        handler.removeCallbacksAndMessages(null);
        if (myRxFFmpegSubscriber != null) {
            myRxFFmpegSubscriber.dispose();
        }
        webView.loadUrl(null);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.destroy();
        llTitle.removeView(webView);
        super.onDestroy();
    }

    /**
     * 用于处理断开人工客服长连接
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            mCountDown--;
            if (mCountDown <= 0){
                if (mUrl.equals(Api.App_Service_Customer_Test)) {
                    isCloseLink = true;
                    // 向html中注入数据，断开连接
                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            webView.evaluateJavascript("javascript:closeLink()",
                                    new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String value) {
                                            //此处为 js 返回的结果
                                        }
                                    });
                        }
                    });
                }
            }else{
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    /**
     * 开始上传视频
     * @param uri
     */
    private void onUploadVideo(Uri uri){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadCallBackAboveL != null) {
                if (uri != null) {
                    mUploadCallBackAboveL.onReceiveValue(new Uri[]{uri});
                    mUploadCallBackAboveL = null;
                }
            }
        }else{
            if (mUploadCallBack != null) {
                if (uri != null) {
                    mUploadCallBack.onReceiveValue(uri);
                    mUploadCallBack = null;
                }
            }
        }
    }

    /**
     * 删除压缩图片和文件
     */
    private void onDeleteImageOrVideo(){
        if (!TextUtils.isEmpty(filePath)){
            File file = new File(filePath);
            //删除系统缩略图
            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
            //删除手机中图片
            file.delete();
        }
        filePath = "";
    }

    /**
     * 开始压缩视频
     */
    private void runFFmpegRxJava(String path) {

//        String text = "ffmpeg -y -i /storage/emulated/0/1/input.mp4 -vf boxblur=25:5 -preset superfast /storage/emulated/0/1/result.mp4";
//
//        String[] commands = text.split(" ");

        openProgressDialog();

        if (myRxFFmpegSubscriber != null) {
            myRxFFmpegSubscriber.dispose();
        }

        myRxFFmpegSubscriber = new MyRxFFmpegSubscriber(this);

        //开始执行FFmpeg命令
        RxFFmpegInvoke.getInstance()
                .runCommandRxJava(getBoxblur(path))
                .subscribe(myRxFFmpegSubscriber);
    }

    /**
     * 压缩视频
     * @param path
     * @return
     */
    private String[] getBoxblur(String path) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(path);
        cmdlist.append("-vf");
        cmdlist.append("boxblur=2:1");
        cmdlist.append("-preset");
        cmdlist.append("superfast");
        cmdlist.append(filePath);
        return cmdlist.build();
    }


    public static class MyRxFFmpegSubscriber extends RxFFmpegSubscriber {

        private WeakReference<CustomerWebViewActivity> mWeakReference;

        public MyRxFFmpegSubscriber(CustomerWebViewActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onFinish() {
            final CustomerWebViewActivity activity = mWeakReference.get();
            if (activity != null) {
                activity.confirmProgressDialog();
            }
        }

        @Override
        public void onProgress(int progress, long progressTime) {
            final CustomerWebViewActivity activity = mWeakReference.get();
            if (activity != null) {
                //progressTime 可以在结合视频总时长去计算合适的进度值
                activity.setProgressDialog(progress, progressTime);
            }
        }

        @Override
        public void onCancel() {
            final CustomerWebViewActivity activity = mWeakReference.get();
            if (activity != null) {
                activity.cancelProgressDialog(0, "");
            }
        }

        @Override
        public void onError(String message) {
            final CustomerWebViewActivity activity = mWeakReference.get();
            if (activity != null) {
                activity.cancelProgressDialog(1, message);
            }
        }
    }

    /**
     * 初始化弹窗
     */
    private void openProgressDialog() {
        //统计开始时间
        startTime = System.nanoTime();
        mProgressDialog = Utils.openProgressDialog(this);
    }

    /**
     * 取消进度条
     *
     * @param type 0：取消，1：压缩出错
     * @param message 报错消息
     */
    private void cancelProgressDialog(int type, String message) {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
        if (type == 1) {
            showDialog(getString(R.string.txt_video_operation_error) + message);
        }
        clearUploadMessage();
    }

    /**
     * 压缩完成
     */
    private void confirmProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
        Uri uri = ImageUtils.getUriFromFile(this, filePath);
        onUploadVideo(uri);
    }

    /**
     * 设置进度条
     */
    private void setProgressDialog(int progress, long progressTime) {
        if (mProgressDialog != null) {
            mProgressDialog.setProgress(progress);
            //progressTime 可以在结合视频总时长去计算合适的进度值
            mProgressDialog.setMessage(getString(R.string.txt_the_processing_time));
        }
    }

    private void showDialog(String message) {
        //统计结束时间
        endTime = System.nanoTime();

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(message + "\n\n" + getString(R.string.txt_video_operation_time) + Utils.convertUsToTime((endTime - startTime) / 1000, false));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {

            }
        });

    }

    /**
     * webview没有选择文件也要传null，防止下次无法执行
     */
    private void clearUploadMessage() {
        if (mUploadCallBackAboveL != null) {
            mUploadCallBackAboveL.onReceiveValue(null);
            mUploadCallBackAboveL = null;
        }
        if (mUploadCallBack != null) {
            mUploadCallBack.onReceiveValue(null);
            mUploadCallBack = null;
        }
    }


}
