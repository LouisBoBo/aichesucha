package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerListeningListComponent;
import com.car.scth.mvp.contract.ListeningListContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.bean.ListenMobileBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceConfigPutBean;
import com.car.scth.mvp.model.putbean.DeviceConfigSetPutBean;
import com.car.scth.mvp.presenter.ListeningListPresenter;
import com.car.scth.mvp.ui.adapter.ListenMobileAdapter;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 白名单列表
 * <p>
 * Created by MVPArmsTemplate on 12/16/2020 18:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ListeningListActivity extends BaseActivity<ListeningListPresenter> implements ListeningListContract.View
        , ListenMobileAdapter.onListenMobileChange {

    @BindView(R.id.toolbar_right)
    TextView tvRight;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private List<ListenMobileBean> mobileBeans;
    private ListenMobileAdapter mAdapter;
    private String mSimei;
    private List<String> mSimeiBeas;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), ListeningListActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerListeningListComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_listening_list;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_add_listen_mobile);
        mSimei = MyApplication.getMyApp().getSimei();
        tvRight.setText(R.string.txt_save);
        tvRight.setTextColor(getResources().getColor(R.color.color_FFFFFF));
        tvRight.setVisibility(View.VISIBLE);
        mobileBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();

        if (!TextUtils.isEmpty(mSimei)){
            mSimeiBeas.add(mSimei);
        }

        getPhoneBook();
        mAdapter = new ListenMobileAdapter(this, mobileBeans);
        listView.setAdapter(mAdapter);
        mAdapter.setListenMobileChange(this);

    }

    @OnClick({R.id.toolbar_right, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                hideKeyboard(view);
                subimitPhoneBook();
                break;
            case R.id.btn_add:
                onAddMobileNumber();
                break;
        }
    }

    //获取电话本
    private void getPhoneBook() {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setType(ResultDataUtils.Config_Phone);

        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);

        if (getPresenter() != null) {
            getPresenter().getPhoneBookConfig(bean);
        }
    }

    /**
     * 提交电话本参数
     */
    private void subimitPhoneBook() {
        //去除号码为空的情况
        Iterator<ListenMobileBean> iterator = mobileBeans.iterator();
        while (iterator.hasNext()) {
            ListenMobileBean bean = iterator.next();
            try {
                if (TextUtils.isEmpty(bean.getMobile())) {
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //去除重复号码
        Set<String> setPhone = new LinkedHashSet<>();
        for (ListenMobileBean bean : mobileBeans) {
            setPhone.add(bean.getMobile());
        }
        mobileBeans.clear();

        int j = 0;
        for (String str : setPhone) {
            ListenMobileBean bean = new ListenMobileBean(++j);
            bean.setMobile(str);
            bean.setContacter("");
            mobileBeans.add(bean);
        }

        JsonArray jsonArray = new JsonArray();
        for (ListenMobileBean bean : mobileBeans) {
            jsonArray.add(bean.getMobile());
        }

        DeviceConfigSetPutBean.ParamsBean.ConfigBean configBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean();
        configBean.setPhone_book(jsonArray.toString());

        DeviceConfigSetPutBean.ParamsBean paramsBean = new DeviceConfigSetPutBean.ParamsBean();
        paramsBean.setConfig(configBean);
        paramsBean.setSimei(mSimeiBeas);

        DeviceConfigSetPutBean bean = new DeviceConfigSetPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Set);
        bean.setModule(ModuleValueService.Module_For_Config_Set);

        if (getPresenter() != null) {
            getPresenter().setDeviceConfig(bean);
        }
    }

    /**
     * 添加号码输入框
     */
    private void onAddMobileNumber() {
        if (mobileBeans.size() >= 10) {
            ToastUtils.show(getString(R.string.txt_white_at_most_10));
            return;
        }
        mobileBeans.add(new ListenMobileBean(mobileBeans.size() + 1));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddMobile(int position, String mobile) {
        mobileBeans.get(position).setMobile(mobile);
    }

    @Override
    public void getPhoneBookConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        String phoneBook = deviceConfigResultBean.getConfig().getPhone_book();
        if (deviceConfigResultBean.isSuccess()) {
            if(!TextUtils.isEmpty(phoneBook)){
                try {
                    JSONArray jsonArray = new JSONArray(phoneBook);
                    Set<String> setPhone = new LinkedHashSet<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String mobilePhone = jsonArray.getString(i);
                        if (!TextUtils.isEmpty(mobilePhone) && !"null".equals(mobilePhone)) {
//                            setPhone.add(mobilePhone);
                            ListenMobileBean bean = new ListenMobileBean(i + 1);
                            bean.setMobile(mobilePhone);
                            bean.setContacter("");
                            mobileBeans.add(bean);
                        }
                    }
                    //去重处理 暂不处理
//                    int j = 0;
//                    for (String str : setPhone) {
//                        ListenMobileBean bean = new ListenMobileBean(++j);
//                        bean.setMobile(str);
//                        bean.setContacter("");
//                        mobileBeans.add(bean);
//                    }
                    if (mobileBeans.size() == 0) {
                        mobileBeans.add(new ListenMobileBean(1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                if (mobileBeans.size() == 0) {
                    mobileBeans.add(new ListenMobileBean(1));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDeviceConfigSuccess(BaseBean baseBean) {
        if (baseBean.isSuccess()) {
            if (mobileBeans.size() == 0) {
                mobileBeans.add(new ListenMobileBean(1));
            }
            ToastUtils.show(getString(R.string.txt_set_success));
            mAdapter.notifyDataSetChanged();
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


}
