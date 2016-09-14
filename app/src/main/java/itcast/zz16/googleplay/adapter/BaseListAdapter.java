package itcast.zz16.googleplay.adapter;

import android.content.Intent;
import android.widget.ListView;

import java.util.List;

import itcast.zz16.googleplay.DetailActivity;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.holder.BaseListHolder;
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
 * <p/>
 * ======================
 */
public abstract class BaseListAdapter extends DefaultAdapter<AppInfo> {
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
}
