package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.RelativeLayout;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.adapter.DefaultAdapter;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file MoreHolder
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class MoreHolder extends BaseHolder<Integer> {
    public static final int HAS_MORE = 0;//有更多数据
    public static final int ERROR = 1;//加载更多失败
    public static final int HAS_NO_MORE = 2;//没有更多数据
    private DefaultAdapter adapter;
    private boolean hasMore;
    private RelativeLayout rl_more_loading;
    private RelativeLayout rl_more_error;

    public MoreHolder(DefaultAdapter adapter, boolean hasMore) {

        this.adapter = adapter;
        this.hasMore = hasMore;
        if (!hasMore) {
            //没有更多数据 自动隐藏加载更多条目
            setData(HAS_NO_MORE);
        }

    }

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.item_more);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    @Override
    public View getContentView() {
        // 就是加载更多条目显示的时候 加载跟多
        if (hasMore) {//  有更多数据 才去调用加载更多
            onload();
        }

        return super.getContentView();
    }

    private void onload() {
        //加载更多数据
        //1. 请求网络数据 2.添加到原来的集合中 3.更新adapter(adapter.notifyDataSetChanged())
        if (adapter != null) {
            adapter.onload();//转交给adapter
//            setData(HAS_MORE);
        }
    }

    @Override
    protected void refreshView(Integer state) {

        if (rl_more_loading != null) {
            rl_more_loading.setVisibility(state == HAS_MORE ? View.VISIBLE : View.GONE);
        }

        if (rl_more_error != null) {
            rl_more_error.setVisibility(state == ERROR ? View.VISIBLE : View.GONE);
        }
    }
}
