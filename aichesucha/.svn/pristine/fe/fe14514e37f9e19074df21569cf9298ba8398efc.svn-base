package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerPayWebViewComponent;
import com.car.scth.mvp.contract.PayWebViewContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.presenter.PayWebViewPresenter;
import com.car.scth.mvp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 充值界面
 * <p>
 * Created by MVPArmsTemplate on 03/01/2021 14:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PayWebViewActivity extends BaseActivity<PayWebViewPresenter> implements PayWebViewContract.View {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;

    private final static String Key_Title = "WebViewActivity.title";
    private final static String Key_Url = "WebViewActivity.url";

    private String mTitle = ""; // 标题
    private String mUrl = "";

    public static Intent newInstance(String title, String url) {
        Intent intent = new Intent(MyApplication.getMyApp(), PayWebViewActivity.class);
        intent.putExtra(Key_Title, title);
        intent.putExtra(Key_Url, url);
        return intent;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayWebViewComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_web_view;//setContentView(id);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitle = getIntent().getStringExtra(Key_Title);
        mUrl = getIntent().getStringExtra(Key_Url);
        setTitle(mTitle);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
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
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //webview中缓存模式
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
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("返回url "+url);
                if (url.startsWith("https://wx.tenpay.com")){
                    if (!Utils.isPkgInstalled(PayWebViewActivity.this, "com.tencent.mm")) {
                        ToastUtils.showShort(getResources().getText(R.string.txt_no_install_wx));
                        return true;
                    }
                }
                if (Api.Pay_Sim_Success_Return.equals(url)) {
                    //微信支付返回  app监听之后结束当前页面
                    startActivity(new Intent(PayWebViewActivity.this,SimDetailActivity.class));
                    finish();
                    return true;
                } else if (url.startsWith("weixin://wap/pay?")) {
                    try {
                        if (Utils.isPkgInstalled(PayWebViewActivity.this, "com.tencent.mm")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } else {
                            ToastUtils.showShort(getResources().getText(R.string.txt_no_install_wx));
                        }
                    } catch (Exception e) {
                        //处理错误
                        e.printStackTrace();

                    }
                    // ------  对alipays:相关的scheme处理 -------
                } else if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        //处理错误
                        e.printStackTrace();
                        ToastUtils.showShort(getResources().getText(R.string.txt_no_install_aliPay));
                    }
                } else {
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Referer", Api.Pay_Sim_Referer);
                    view.loadUrl(url, extraHeaders);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                mProgress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            startActivity(new Intent(PayWebViewActivity.this,SimDetailActivity.class));
            finish(); //支付宝返回 直接当前页面
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        webView.loadUrl(null);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.destroy();
        llTitle.removeView(webView);
        super.onDestroy();
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
}
