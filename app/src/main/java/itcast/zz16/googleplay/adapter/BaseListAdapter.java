package itcast.zz16.googleplay.adapter;

import android.content.Intent;
import android.widget.ListView;

import java.util.List;

import itcast.zz16.googleplay.DetailActivity;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.db.DownloadDbHelper;
import itcast.zz16.googleplay.db.DownloadInfo;
import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.holder.BaseListHolder;
import itcast.zz16.googleplay.manager.DownloadManager;
import itcast.zz16.googleplay.utils.ThreadUtils;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseListAdapter
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public abstract class BaseListAdapter extends DefaultAdapter<AppInfo> implements DownloadManager.DownloadObserver {
    public BaseListAdapter(List<AppInfo> datas, ListView listView) {
        super(datas, listView);
    }

    @Override
    protected BaseHolder<AppInfo> getHolder() {
        return new BaseListHolder();
    }

    @Override
    protected void onInnerItemClick(int position) {

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra("packageName", datas.get(position).packageName);
        UIUtils.startActivity(intent);
    }

    /*注册观察者*/
    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    /*反注册观察者*/
    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }
    private void refreshHolder(final DownloadInfo info) {
        List<BaseHolder> displayedHolders = getDisplayHolders();
        for (int i = 0; i < displayedHolders.size(); i++) {
            BaseHolder baseHolder = displayedHolders.get(i);
            if (baseHolder instanceof BaseListHolder) {
                final BaseListHolder holder = (BaseListHolder) baseHolder;
                AppInfo appInfo = holder.getData();
                if (appInfo.id == info.getId().longValue()) {
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.refreshState(info.getDownloadState(),
                                    (int) (info.getCurrentSize() * 100 / info
                                            .getAppSize()));
                        }
                    });
                }
            }
        }
    }
}
