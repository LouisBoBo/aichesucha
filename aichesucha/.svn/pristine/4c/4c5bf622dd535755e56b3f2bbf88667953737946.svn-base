package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRechargeComponent;
import com.car.scth.mvp.contract.RechargeContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.RechargePackagesResultBean;
import com.car.scth.mvp.model.bean.RechargePayBean;
import com.car.scth.mvp.model.putbean.RechargePackagesPutBean;
import com.car.scth.mvp.presenter.RechargePresenter;
import com.car.scth.mvp.ui.adapter.BasicPackagesAdapter;
import com.car.scth.mvp.ui.adapter.FlowPackagesAdapter;
import com.car.scth.mvp.ui.adapter.RechargePayAdapter;
import com.car.scth.mvp.ui.adapter.SmsPackagesAdapter;
import com.car.scth.mvp.ui.view.NoScrollListView;

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
 * Description: sim卡充值页面
 * <p>
 * Created by MVPArmsTemplate on 11/19/2020 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeContract.View {

    @BindView(R.id.listView_basic)
    NoScrollListView listViewBasic; // 基础套餐
    @BindView(R.id.rl_basic_package)
    RelativeLayout rlBasicPackage; // 基础套餐
    @BindView(R.id.listView_flow)
    NoScrollListView listViewFlow; // 流量
    @BindView(R.id.listView_sms)
    NoScrollListView listViewSms; // 短信
    @BindView(R.id.listView_pay_type)
    NoScrollListView listViewPayType; // 支付方式
    @BindView(R.id.btn_pay)
    Button btnPay; // 支付

    private static final String KEY_ICCID = "key_iccid";
    private String mIccid;

    private List<RechargePackagesResultBean.AddoptionalBean> smsInfoBeans; // 短信套餐信息
    private SmsPackagesAdapter smsAdapter;

    private List<RechargePackagesResultBean.ComeonBean> flowInfoBeans; // 流量套餐信息
    private FlowPackagesAdapter flowAdapter;

    private List<RechargePackagesResultBean.RenewalBean> basicInfoBeans; // 基础套餐信息
    private BasicPackagesAdapter basicAdapter;

    private List<RechargePayBean> payBeans; // 支付类型
    private RechargePayAdapter payAdapter;

    public static Intent newInstance(String iccid) {
        Intent intent = new Intent(MyApplication.getMyApp(), RechargeActivity.class);
        intent.putExtra(KEY_ICCID, iccid);
        return intent;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRechargeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recharge;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_sim_recharge));
        smsInfoBeans = new ArrayList<>();
        flowInfoBeans = new ArrayList<>();
        basicInfoBeans = new ArrayList<>();
        payBeans = new ArrayList<>();
        mIccid = getIntent().getStringExtra(KEY_ICCID);
        btnPay.setVisibility(View.GONE);

        smsAdapter = new SmsPackagesAdapter(this, R.layout.item_sim_recharge_packages, smsInfoBeans);
        listViewSms.setAdapter(smsAdapter);

        flowAdapter = new FlowPackagesAdapter(this, R.layout.item_sim_recharge_packages, flowInfoBeans);
        listViewFlow.setAdapter(flowAdapter);

        basicAdapter = new BasicPackagesAdapter(this, R.layout.item_sim_recharge_packages, basicInfoBeans);
        listViewBasic.setAdapter(basicAdapter);

        payBeans.add(new RechargePayBean(0, getString(R.string.txt_wechat_pay), R.drawable.icon_wechat_pay, true));
        payBeans.add(new RechargePayBean(1, getString(R.string.txt_alipay), R.drawable.icon_alipay, false));
        payAdapter = new RechargePayAdapter(this, R.layout.item_recharge_pay, payBeans);
        listViewPayType.setAdapter(payAdapter);
        listViewPayType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (RechargePayBean bean : payBeans){
                    bean.setSelect(false);
                }
                payBeans.get(position).setSelect(true);
                payAdapter.notifyDataSetChanged();
            }
        });

        listViewFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFlowPackagesClick(position);
            }
        });
        listViewSms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSmsPackagesClick(position);
            }
        });
        listViewBasic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onBasicPackagesClick(position);
            }
        });

        getRechargePackages();
    }

    /**
     * 获取sim卡充值套餐
     */
    private void getRechargePackages() {
        RechargePackagesPutBean.ParamsBean paramsBean = new RechargePackagesPutBean.ParamsBean();
        paramsBean.setIccid(mIccid);

        RechargePackagesPutBean bean = new RechargePackagesPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Sim_Recharge_Packages);
        bean.setModule(ModuleValueService.Module_For_Sim_Recharge_Packages);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getRechargePackages(bean);
        }
    }

    /**
     * 短信点击操作
     *
     * @param position
     */
    private void onSmsPackagesClick(int position) {
        for (RechargePackagesResultBean.AddoptionalBean bean : smsInfoBeans) {
            bean.setSelect(false);
        }
        smsInfoBeans.get(position).setSelect(true);
        smsAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.ComeonBean bean : flowInfoBeans) {
            bean.setSelect(false);
        }
        flowAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.RenewalBean bean : basicInfoBeans) {
            bean.setSelect(false);
        }
        basicAdapter.notifyDataSetChanged();
    }

    /**
     * 流量点击操作
     *
     * @param position
     */
    private void onFlowPackagesClick(int position) {
        for (RechargePackagesResultBean.AddoptionalBean bean : smsInfoBeans) {
            bean.setSelect(false);
        }
        smsAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.ComeonBean bean : flowInfoBeans) {
            bean.setSelect(false);
        }
        flowInfoBeans.get(position).setSelect(true);
        flowAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.RenewalBean bean : basicInfoBeans) {
            bean.setSelect(false);
        }
        basicAdapter.notifyDataSetChanged();
    }

    /**
     * 基础套餐点击操作
     *
     * @param position
     */
    private void onBasicPackagesClick(int position) {
        for (RechargePackagesResultBean.AddoptionalBean bean : smsInfoBeans) {
            bean.setSelect(false);
        }
        smsAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.ComeonBean bean : flowInfoBeans) {
            bean.setSelect(false);
        }
        flowAdapter.notifyDataSetChanged();

        for (RechargePackagesResultBean.RenewalBean bean : basicInfoBeans) {
            bean.setSelect(false);
        }
        basicInfoBeans.get(position).setSelect(true);
        basicAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
    }

    @Override
    public void getRechargePackagesSuccess(RechargePackagesResultBean rechargePackagesResultBean) {
        if (rechargePackagesResultBean.getAddoptional() != null) {
            smsInfoBeans.addAll(rechargePackagesResultBean.getAddoptional());
        }
        if (rechargePackagesResultBean.getComeon() != null) {
            flowInfoBeans.addAll(rechargePackagesResultBean.getComeon());
        }
        if (rechargePackagesResultBean.getRenewal() != null) {
            basicInfoBeans.addAll(rechargePackagesResultBean.getRenewal());
        }

        if (flowInfoBeans.size() > 0) {
            flowInfoBeans.get(0).setSelect(true);
        } else if (smsInfoBeans.size() > 0) {
            smsInfoBeans.get(0).setSelect(true);
        } else if (basicInfoBeans.size() > 0) {
            basicInfoBeans.get(0).setSelect(true);
        }
        flowAdapter.notifyDataSetChanged();
        smsAdapter.notifyDataSetChanged();
        basicAdapter.notifyDataSetChanged();
    }
}
