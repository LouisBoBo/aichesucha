package com.car.scth.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerDataCenterComponent;
import com.car.scth.mvp.contract.DataCenterContract;
import com.car.scth.mvp.model.bean.DataCenterBean;
import com.car.scth.mvp.presenter.DataCenterPresenter;
import com.car.scth.mvp.ui.activity.AlarmRecordActivity;
import com.car.scth.mvp.ui.adapter.DataCenterAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2020 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DataCenterFragment extends BaseFragment<DataCenterPresenter> implements DataCenterContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<DataCenterBean> dataCenterBeans;
    private DataCenterAdapter mAdapter;

    public static DataCenterFragment newInstance() {
        DataCenterFragment fragment = new DataCenterFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDataCenterComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_center, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        onAddDataCenter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DataCenterAdapter(R.layout.item_data_center, dataCenterBeans);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onDataCenterFunctionClick(dataCenterBeans.get(position).getId());
            }
        });
    }

    /**
     * 添加功能数据
     */
    private void onAddDataCenter(){
        dataCenterBeans = new ArrayList<>();
        dataCenterBeans.add(new DataCenterBean(0, getString(R.string.txt_alarm_record), R.drawable.icon_alarm_record));
        dataCenterBeans.add(new DataCenterBean(1, getString(R.string.txt_offline_statistics), R.drawable.icon_offline_statistics));
        dataCenterBeans.add(new DataCenterBean(2, getString(R.string.txt_parking_report), R.drawable.icon_parking_report));
        dataCenterBeans.add(new DataCenterBean(3, getString(R.string.txt_traffic_statistics), R.drawable.icon_traffic_statistics));
        dataCenterBeans.add(new DataCenterBean(4, getString(R.string.txt_stay_often), R.drawable.icon_stay_often));
        dataCenterBeans.add(new DataCenterBean(5, getString(R.string.txt_remaining_battery), R.drawable.icon_remaining_battery));
        dataCenterBeans.add(new DataCenterBean(6, getString(R.string.txt_mileage_report), R.drawable.icon_mileage_report));
        dataCenterBeans.add(new DataCenterBean(7, getString(R.string.txt_total_mileage_report), R.drawable.icon_total_mileage_report));
        dataCenterBeans.add(new DataCenterBean(8, getString(R.string.txt_command_query), R.drawable.icon_command_query));
        dataCenterBeans.add(new DataCenterBean(9, getString(R.string.txt_operation_record), R.drawable.icon_operation_record));
    }

    /**
     * 功能列表点击事件
     * @param id
     */
    private void onDataCenterFunctionClick(int id){
        switch (id){
            case 0:
                launchActivity(AlarmRecordActivity.newInstance());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
        }
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某�?Fragment 对象执行一些方�?
     * 建议在有多个需要与外界交互的方法时, 统一�?{@link Message}, 通过 what 字段来区分不同的方法, �?{@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操�? 可以起到分发的作�?     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周�? 如果调用 {@link #setData(Object)} 方法�?{@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用�?Presenter 的方�? 是会报空�? 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调�?{@link #setData(Object)}, �?{@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以�?{@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }
}
