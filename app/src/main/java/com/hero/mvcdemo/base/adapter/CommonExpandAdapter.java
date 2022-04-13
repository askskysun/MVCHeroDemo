package com.hero.mvcdemo.base.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;

import com.hero.mvcdemo.tools.ViewsizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 可展开ExpandableListView
 * 适配器模板
 */
public abstract class CommonExpandAdapter<T> extends BaseExpandableListAdapter implements ExpandableListAdapter {
    protected static final String TAG = CommonExpandAdapter.class.getSimpleName();

    protected Context mContext;
    /**
     * 父布局，Item xml的ID
     */
    protected int layoutIDGroup;
    /**
     * 子布局，Item xml的ID
     */
    protected int layoutIDChild;
    /**
     * Item填充数据
     */
    protected List<T> lDatas = new ArrayList<>();

    /**
     * 单Item Type时构造函数
     * 使用此构造方法，需要在子类中重写一下两个方法
     * getLayoutGroupId();getLayoutChlidId();
     *
     * @param mContext 上下文
     * @param lDatas   Item数据集
     */
    public CommonExpandAdapter(Context mContext, List<T> lDatas) {
        this(mContext, lDatas, -1, -1);
    }

    /**
     * 单Item Type时构造函数
     *
     * @param context       上下文
     * @param lDatas        Item数据集
     * @param layoutIDGroup 父布局
     * @param layoutIDChild 子布局
     */
    public CommonExpandAdapter(Context context, List<T> lDatas, int layoutIDGroup, int layoutIDChild) {
        this.mContext = context;
        if (lDatas != null) {
            this.lDatas = lDatas;
        }
        this.layoutIDGroup = layoutIDGroup;
        this.layoutIDChild = layoutIDChild;
    }

    /**
     * 获取 父布局 Item布局文件的ID
     *
     * @param groupPosition
     * @return
     */
    public int getLayoutGroupId(int groupPosition) {
        return layoutIDGroup;
    }

    /**
     * 获取Item布局文件的ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public int getLayoutChlidId(int groupPosition, int childPosition) {
        return layoutIDChild;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getGroupCount() {
        return lDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getItemChildCount(groupPosition);
    }

    @Override
    public T getGroup(int groupPosition) {
        try {
            return lDatas.get(groupPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 需子类重写
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getItemChild(groupPosition, childPosition);
    }

    protected abstract Object getItemChild(int groupPosition, int childPosition);

    protected abstract int getItemChildCount(int groupPosition);

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 给每个Item设置数据
     * 父布局
     *
     * @param holder holder句柄对象
     * @param group  item每一项数据值
     */
    public abstract void getGroupItemView(ViewHolder holder, boolean isExpanded, int groupPosition, T group);

    /**
     * 给每个Item设置数据
     * 子布局
     *
     * @param holder holder句柄对象
     * @param item   item每一项数据值
     */
    public abstract void getChlidItemView(ViewHolder holder, int groupPosition, int childPosition, Object item);

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        int itemID = getLayoutGroupId(groupPosition);

        ViewHolder holder = ViewHolder.getInstance(mContext, convertView, parent, itemID, groupPosition);
        T group = getGroup(groupPosition);
        if (group == null) {
            ViewsizeUtils.viewSizeLin(mContext, holder.getConvertView(), LinearLayout.LayoutParams.MATCH_PARENT, 1);    //传0 会被系统忽略
            view = convertView;
        } else {
            ViewsizeUtils.viewSizeLin(mContext, holder.getConvertView(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            getGroupItemView(holder, isExpanded, groupPosition, group);
            view = holder.getConvertView();
        }
        //防止为空  https://bugly.qq.com/v2/crash-reporting/crashes/bdc9a36b4f/17783/report?pid=1&crashDataType=undefined  解决方案：ListView的adapter的getView方法返回了null
        if (view == null) {
            view = new View(mContext);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View subconvertView, ViewGroup parent) {
        View view;
        int itemID = getLayoutChlidId(groupPosition, childPosition);
        ViewHolder holder = ViewHolder.getInstance(mContext, subconvertView, parent, itemID, groupPosition);
        Object child = getChild(groupPosition, childPosition);
        if (child == null) {
            ViewsizeUtils.viewSizeLin(mContext, holder.getConvertView(), LinearLayout.LayoutParams.MATCH_PARENT, 1);
            view = subconvertView;
        } else {
            ViewsizeUtils.viewSizeLin(mContext, holder.getConvertView(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            getChlidItemView(holder, groupPosition, childPosition, child);
            view = holder.getConvertView();
        }
        //防止为空  https://bugly.qq.com/v2/crash-reporting/crashes/bdc9a36b4f/17783/report?pid=1&crashDataType=undefined
        if (view == null) {
            view = new View(mContext);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}