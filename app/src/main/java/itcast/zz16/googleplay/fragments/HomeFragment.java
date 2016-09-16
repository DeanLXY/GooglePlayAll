package itcast.zz16.googleplay.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import itcast.zz16.googleplay.DetailActivity;
import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.adapter.BaseListAdapter;
import itcast.zz16.googleplay.adapter.DefaultAdapter;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.holder.HomePictureHolder;
import itcast.zz16.googleplay.protocol.HomeProtocol;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.BaseListView;

/**
 * A simple {@link Fragment} subclass.
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfo> datas;
    private List<String> pictures;
    private BaseListAdapter baseListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();// 手动调用 解决  首页 自动请求网络问题
    }


    @Override
    protected int load() {
        // 1. 请求服务器数据 2. 缓存  3.复用缓存  4.解析
        HomeProtocol protocol = new HomeProtocol();
        datas = protocol.load(0);
        pictures = protocol.getPictures();
        return checkDatas(datas);
    }

    @Override
    protected View createSuccessView() {
        SwipeRefreshLayout refreshLayout = new SwipeRefreshLayout(UIUtils.getContext());
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        //停止刷新
//        refreshLayout.setRefreshing(false);

        ListView listView = new BaseListView(UIUtils.getContext());
        refreshLayout.addView(listView);
        // holder  轮播图holder
        HomePictureHolder homePictureHolder = new HomePictureHolder();// initView
        homePictureHolder.setData(pictures);// --- >refreshView

        listView.addHeaderView(homePictureHolder.getContentView());

        // 引用传递
        /**
         * 加载更多数据
         * @return
         */ // page =1  page= 2
//                page++
        baseListAdapter = new BaseListAdapter(datas, listView) {
            @Override
            protected List<AppInfo> loadMore() {
                HomeProtocol protocol = new HomeProtocol();
                List<AppInfo> newDatas = protocol.load(datas.size());
                return newDatas;
            }
        };
        listView.setAdapter(baseListAdapter);
        return refreshLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (baseListAdapter != null)
            baseListAdapter.stopObserver();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (baseListAdapter != null)
            baseListAdapter.startObserver();
    }
}
