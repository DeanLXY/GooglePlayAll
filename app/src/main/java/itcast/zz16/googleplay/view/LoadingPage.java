package itcast.zz16.googleplay.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.utils.ThreadUtils;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file LoadingPage
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * 对BaseFragment做代码的拆分
 */
public abstract class LoadingPage extends FrameLayout {
    private View loadingView;//加载中的view
    private View errorView;//加载失败view
    private View emptyView;//加载为空view
    private View successView;//加载成功view
    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();// 将 几种不同的界面添加到帧布局中
    }

    /**
     * 将几种不同的界面添加到帧布局中
     */
    private void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();
           this.addView(loadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        }

        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        }

        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));

        }

        showpage();// 根据状态切换界面
        // 没有添加成功界面    请求成功  再去添加
    }

    /**
     * 创建加载中的view对象
     *
     * @return
     */
    private View createLoadingView() {

        // xml --- View  自定义组合控件
//        View.inflate(UIUtils.getContext(), R.layout.page_loading,null);
        View view = UIUtils.inflate(R.layout.page_loading);
        return view;
    }

    /**
     * 创建加载失败的view对象
     *
     * @return
     */
    private View createErrorView() {
        // xml-- view
        View view = UIUtils.inflate(R.layout.page_error);
        view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show(); //根据服务器返回的状态切换为不同的界面
            }
        });
        return view;
    }

    /**
     * 创建一个加载为空的View对象
     *
     * @return
     */
    private View createEmptyView() {
        // xml -- -View
        return UIUtils.inflate(R.layout.page_empty);
    }
    public static final int STATU_UNKNOWN = 0;//未知状态
    public static final int STATU_LOADING = 1;//加载中状态
    public static final int STATU_ERROR = 2;//失败状态
    public static final int STATU_EMPTY = 3;//为空状态
    public static final int STATU_SUCCESS = 4;//成功状态

    private int statu = STATU_LOADING;//默认加载中
    /**
     * 根据状态切换为不同的界面
     */
    private void showpage() {

        if (loadingView != null) {
            loadingView.setVisibility(statu == STATU_LOADING || statu == STATU_UNKNOWN ? View.VISIBLE : View.INVISIBLE);
        }

        if (errorView != null) {
            errorView.setVisibility(statu == STATU_ERROR ? View.VISIBLE : View.INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(statu == STATU_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        // 请求服务器成功 去创建成功界面
        if (statu == STATU_SUCCESS) {
            if (successView == null) {

                successView = createSuccessView();
                this.addView(successView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
            }
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 根据服务器返回状态切换为不同的界面
     */
    public void show() {
        if (statu == STATU_ERROR){
            statu = STATU_LOADING;// 如果是错误状态 请求服务器时候 重新置为 加载中的状态
        }

        showpage();//  状态重置后重新切换界面
        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {

                statu = load();
                //切换到主线程
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showpage();
                    }
                });
            }
        });
    }

    /**
     * 请求服务器数据 返回对应的状态
     * ctrl + shift + G
     * @return
     */
    protected abstract int load();

    /**
     * 创建成功界面
     * @return
     */
    protected abstract View createSuccessView();
}
