package com.car.scth.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.car.scth.R;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.wxapi.helper.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调页
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0: // 支付成功
                    break;
                case -1: // 支付失败
                    ToastUtils.show(getResources().getString(R.string.txt_wx_pay_fail));
                    break;
                case -2: // 支付取消
                    ToastUtils.show(getResources().getString(R.string.txt_wx_pay_cancel));
                    break;

                default:
                    break;
            }
        }
        finish();
    }
}