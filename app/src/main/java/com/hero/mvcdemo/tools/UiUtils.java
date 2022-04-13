package com.hero.mvcdemo.tools;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Scroller;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ui处理工具类
 */
public class UiUtils {

    /**
     * 考虑使用了反射  有影响风险  暂不使用
     * 禁止EditText弹起软键盘而显示光标
     * https://blog.csdn.net/funcyhong/article/details/101098129
     *
     * @param window
     * @param editText
     */
    public static void hideEditTextSoftInputMethod(Window window, EditText editText) {
        if (window == null || editText == null) {
            return;
        }
        try {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            String methodName = null;
            if (currentVersion >= 16) {
                // 4.2
                methodName = "setShowSoftInputOnFocus";
            } else if (currentVersion >= 14) {
                // 4.0
                methodName = "setSoftInputShownOnFocus";
            }

            if (methodName == null) {
                editText.setInputType(InputType.TYPE_NULL);
            } else {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                try {
                    setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                    setShowSoftInputOnFocus.setAccessible(true);
                    setShowSoftInputOnFocus.invoke(editText, false);
                } catch (NoSuchMethodException e) {
                    editText.setInputType(InputType.TYPE_NULL);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //显示光标
            editText.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reFreshClose(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 关闭软键盘
     * 传入焦点的View
     * 在Activity 、 Dialog中调用 getCurrentFocus() 获取
     */
    public static void closeSoftInput(View view) {
        try {
            if (view == null) {
                return;
            }

            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启软键盘
     *
     * @param view
     */
    public static void openSoftInput(View view) {
        try {
            if (view == null) {
                return;
            }

            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftInput(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示、隐藏软键盘
     *
     * @param activity 上下文
     */
    public static void toggleKeyBoardStatus(Handler handler, final Activity activity) {
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleKeyBoardStatus(Activity activity) {
        try {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示、隐藏软键盘
     *
     * @param activity     上下文
     * @param showKeyBoard 是否显示键盘
     */
    public static void toggleKeyBoardStatus(Activity activity, boolean showKeyBoard) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (showKeyBoard) {// 显示键盘
            im.showSoftInputFromInputMethod(activity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //根据当前状态，显示则隐藏，隐藏则显示
//            im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {// 隐藏键盘
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void toggleKeyBoardStatus(Handler handler, final View view) {
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                }
            }, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleKeyBoardStatus(View view) {
        try {
            InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);  普通的可以通过这个  默认滑动到最底
     * Listview 滑动到最底部
     *
     * @param listview
     * @param baseAdapter
     */
    public static void scrolListViewToBottom(final ListView listview, final BaseAdapter baseAdapter) {
        if (listview == null || baseAdapter == null) {
            return;
        }
        listview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                try {
                    listview.setSelection(baseAdapter.getCount() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //scrollView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
    //scrollView.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部

    public static void setViewShowState(View view, int visibility) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    //光标移到最后
    public static void setEditSelectionLast(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setSelection(editText.getText().length());
    }

    /**
     * 屏幕旋转  处理工具类
     *
     * @param activity
     */
    public static void resolveByClick(Activity activity) {
        if (activity == null) {
            return;
        }
        //竖屏
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 让listview停止下来
     *
     * @return
     */
    public static void setListViewScrollStop(ListView listView) {
        if (listView != null) {
            listView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        }
    }

    public static void setViewPagerScroller(Context context, ViewPager viewPager, final int size) {

        try {

            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(context, (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * size);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(viewPager, scroller);
        } catch (Exception e) {
            // Do nothing.
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void MoveToPosition(RecyclerView mRecyclerView, int n) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    /**
     * 根据view的ontouch方法 转化成onclick事件
     *
     * @param touchView
     * @param onClickListener
     */
    public static void setTouchToClick(View touchView, final View.OnClickListener onClickListener) {
        if (touchView == null || onClickListener == null) {
            return;
        }

        final GestureDetector mGestureDetector = new GestureDetector(touchView.getContext()
                , new SingleTapConfirm(touchView, onClickListener));

        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    /**
     * 使用gestureDetector来监听点击事件
     * https://www.jianshu.com/p/333ee22bd8d3
     */
    private static class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        private View.OnClickListener onClickListener;
        private View view;

        public SingleTapConfirm(View view, View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            this.view = view;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            //do something
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            return true;
        }
    }

    /**
     * 获取dialog依赖的Activity
     *
     * @param cont
     * @return
     */
    public static Activity scanForActivity(Context cont) {
        try {
            if (cont == null) {
                return null;
            } else if (cont instanceof Activity) {
                return (Activity) cont;
            } else if (cont instanceof ContextWrapper) {
                return scanForActivity(((ContextWrapper) cont).getBaseContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error er) {
            er.printStackTrace();
        }

        return null;
    }
}
