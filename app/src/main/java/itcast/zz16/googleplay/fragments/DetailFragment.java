package itcast.zz16.googleplay.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.holder.DetailBottomHolder;
import itcast.zz16.googleplay.holder.DetailDesHolder;
import itcast.zz16.googleplay.holder.DetailInfoHolder;
import itcast.zz16.googleplay.holder.DetailSafeHolder;
import itcast.zz16.googleplay.holder.DetailScreenHolder;
import itcast.zz16.googleplay.protocol.DetailProtocol;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.LoadingPage;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DetailFragment
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DetailFragment extends BaseFragment {

    @Bind(R.id.bottom_layout)
    FrameLayout bottomLayout;
    @Bind(R.id.detail_info)
    FrameLayout detailInfo;
    @Bind(R.id.detail_safe)
    FrameLayout detailSafe;
    @Bind(R.id.detail_screen)
    HorizontalScrollView detailScreen;
    @Bind(R.id.detail_des)
    FrameLayout detailDes;
    private AppInfo appInfo;
    private String packageName;
    private DetailBottomHolder detailBottomHolder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        packageName = bundle.getString("packageName");
        show();//请求网络数据
        setHasOptionsMenu(true);// 使用菜单

    }

    @Override
    protected int load() {

        // 数据请求  缓存  解析 复用缓存
        DetailProtocol protocol = new DetailProtocol(packageName);
        appInfo = protocol.load(0);
        if (appInfo == null) {
            //失败
            return LoadingPage.STATU_ERROR;
        } else {
            return LoadingPage.STATU_SUCCESS;
        }
    }

    @Override
    protected View createSuccessView() {
        // xml ---- View
        View view = UIUtils.inflate(R.layout.fragment_detail);

        ButterKnife.bind(this, view);

        //  详情界面信息
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();// initView 创建view  初始化view
        detailInfoHolder.setData(appInfo);// --->refreshView
        detailInfo.addView(detailInfoHolder.getContentView());

        //截图
        DetailScreenHolder detailScreenHolder = new DetailScreenHolder();//initView
        detailScreenHolder.setData(appInfo);// -->refreshView
        detailScreen.addView(detailScreenHolder.getContentView());//holder维护的view对象添加到 父view中


        //安全扫描
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        detailSafeHolder.setData(appInfo);
        detailSafe.addView(detailSafeHolder.getContentView());

        //简介
        DetailDesHolder detailDesHolder = new DetailDesHolder();// initView
        detailDesHolder.setData(appInfo);// --->refreshView
        detailDes.addView(detailDesHolder.getContentView());


        //下载
        // initView
        detailBottomHolder = new DetailBottomHolder();
        detailBottomHolder.setData(appInfo);
        bottomLayout.addView(detailBottomHolder.getContentView());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (detailBottomHolder != null)
            detailBottomHolder.stopObserver();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    //处理ActionBar上按钮的点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
