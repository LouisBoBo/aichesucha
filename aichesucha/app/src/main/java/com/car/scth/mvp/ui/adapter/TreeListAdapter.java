package com.car.scth.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.mvp.model.entity.TreeData;

import java.util.ArrayList;
import java.util.List;


public class TreeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    //上下文
    private Context contextx;

    //列表的所有项
    private List<TreeData> allList;
    //列表需要显示的项
    private List<TreeData> ShownList;

    //父节点的ID,如果该节点展开就把节点的ID存储在这
    private List<String> shrinkIdList;

    private FamilyAdapterListener familyAdapterListener;

    public TreeListAdapter(Context ctx, List<TreeData> allList, FamilyAdapterListener familyAdapterListener) {
        this.contextx = ctx;
        this.allList = allList;
        ShownList = new ArrayList<>(allList.size());
        shrinkIdList = new ArrayList<>();
        this.familyAdapterListener = familyAdapterListener;


//        这段注释是把所有负极添加进 shrinkIdList里面让其全部展开 否则全部关闭
//        for (int i = 0; i < allList.size(); i++) {
//            if (allList.get(0).getHasChild()) {
//            }
//        }
        shrinkIdList.add(allList.get(0).getFamilySid());
        listProcessing();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseViewHolder(LayoutInflater.from(contextx).inflate(R.layout.item_tree_list, viewGroup, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        final TreeData treeData = ShownList.get(position);
        int level = treeData.getLevel();
        String sid = treeData.getFamilySid();
        boolean hasChild = treeData.getHasChild();
        String name = treeData.getFamilyName();
        LinearLayout linearLayout = baseViewHolder.getView(R.id.tree_item);
        TextView tvCompanyName = baseViewHolder.getView(R.id.tv_company_name);
        String str1 = String.format(name + "<font color=\"#999999\">%s", " (" + treeData.getFamilyCount() + ")");
        tvCompanyName.setText(Html.fromHtml(str1));
        ImageView leftImg = baseViewHolder.getView(R.id.iv_spread_level_0);
        ImageView iv_family_more = baseViewHolder.getView(R.id.iv_family_more);


        if (level == 0) {
            leftImg.setVisibility(View.INVISIBLE);
            tvCompanyName.setTypeface(Typeface.DEFAULT_BOLD);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) leftImg.getLayoutParams();
            params.setMargins(ArmsUtils.dip2px(contextx, -15), 0, 0, 0);
            leftImg.setLayoutParams(params);
        } else {
            if (treeData.getFamilyCount() > 0) { //子级个数大于0
                //判断该级是否有子级如果有把左边的图标显示
                leftImg.setVisibility(View.VISIBLE);
//            根据是否展开显示对应的图标
                if (shrinkIdList.contains(sid)) {
                    leftImg.setImageResource(R.drawable.down_gray);
                } else {
                    leftImg.setImageResource(R.drawable.right_gray);
                }
            } else {
                //没有的话把图标隐藏了
                leftImg.setVisibility(View.INVISIBLE);
            }

            //设置左边的外边距以达到层级的效果
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) leftImg.getLayoutParams();
            params.setMargins(ArmsUtils.dip2px(contextx, 12 * level - 8), 0, 0, 0);
            leftImg.setLayoutParams(params);
        }

        //该项被点击
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                如果该项展开就把他清出shrinkIdList,反之加进来
                if (shrinkIdList.contains(sid)) {
                    shrinkIdList.remove(sid);
                } else {
                    shrinkIdList.add(sid);
                }
                if (!treeData.isClick()) {
                    familyAdapterListener.itemClick(sid, level);
                    treeData.setClick(true);
                } else {
                    //处理一下新的List
                    listProcessing();
                    notifyDataSetChanged();
                }
            }
        });
        iv_family_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyAdapterListener.moreFamilyFunction(sid,name,view, level);
            }
        });
    }

    public interface FamilyAdapterListener {
        void itemClick(String sid, int level);

        void moreFamilyFunction(String sid,String name,View v, int level);
    }

    //这个方法用来处理列表显示的内容,先遍历所有列表，然后判断是否需要展开
    public void listProcessing() {
        int shrinkLevel;
        ShownList.clear();
        for (int i = 0; i < allList.size(); i++) {
            TreeData treeData = allList.get(i);
            //如果发现遍历到的当前ID在shrinkIdList里面存在，说明需要展开直接把他加进要显示的列表即可
            if (shrinkIdList.contains(treeData.getFamilySid())) {
                ShownList.add(treeData);
            } else {
                //否则先显示该项因为他是父级
                ShownList.add(treeData);
//                把当的层级拿出来
                shrinkLevel = treeData.getLevel();
                i++;
//                下面这个循环用来过滤掉那些折叠的项 把该区间的层级比当前大的过滤掉
                for (; i < allList.size(); i++) {
//                    如果发现比当前大就跳出循环,否则执行下一个
                    if (allList.get(i).getLevel() > shrinkLevel) {
                        continue;
                    } else {
                        i--;
                        break;
                    }
                }
            }
        }
    }

    //需要展开的父组织id 恢复默认展开0级
    public void setShrinkIdListDefault() {
        if (shrinkIdList != null) {
            shrinkIdList.clear();
            shrinkIdList.add(allList.get(0).getFamilySid());
        }
    }

    @Override
    public int getItemCount() {
        return ShownList.size();
    }
}
