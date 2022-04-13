package com.hero.mvcdemo.tools;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 *
 * 只要在设置ListView的Adapter后调用此静态方法即可让ListView正确的显示在其父ListView的ListItem中。
 * 但是要注意的是，子ListView的每个Item必须是LinearLayout，不能是其他的，
 * 因为其他的Layout(如RelativeLayout)没有重写onMeasure()，所以会在onMeasure()时抛出异常。
 * 在ScrollView中嵌套ListView(或者ScrollView)的另外一个问题就是，子ScrollView中无法滑动的(如果它没有显示完全的话)
 *
 */
public class ListviewHUtils {
    //  间隔   增加高度
    public static void setListViewHeightBasedOnChildren(ListView listView , int dividerHeight , int addHeigt) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getRightStr()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight() ) * (listAdapter.getCount() +dividerHeight)) +DensityUtils.dip2px(listView.getContext() ,addHeigt);
        //20   ListView的padding
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    public static void setGridViewHeightBasedOnChildren(GridView listView, int dividerHeight , int addHeigt) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getRightStr()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getRightStr()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight + listAdapter.getCount() * DensityUtils.dip2px(listView.getContext() ,dividerHeight) + DensityUtils.dip2px(listView.getContext() ,addHeigt);
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
}
