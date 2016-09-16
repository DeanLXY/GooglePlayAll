package itcast.zz16.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.holder.MoreHolder;
import itcast.zz16.googleplay.utils.ThreadUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DefaultAdapter
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public abstract class DefaultAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener, AbsListView.RecyclerListener {

    private static final int ITEM_MORE = 0;//加载更多
    private static final int ITEM_DEFAULT = 1; //默认条目类型
    private final ArrayList<BaseHolder> mDisplayHolders;
    protected List<T> datas;
    private ListView listView;
    private MoreHolder moreHolder;

    public DefaultAdapter(List<T> datas, ListView listView) {
        mDisplayHolders = new ArrayList<BaseHolder>();
        this.datas = datas;
        this.listView = listView;
        listView.setOnItemClickListener(this);
        listView.setRecyclerListener(this);
    }

    public List<BaseHolder> getDisplayHolders() {
        synchronized (mDisplayHolders) {
            return new ArrayList<BaseHolder>(mDisplayHolders);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        position -= listView.getHeaderViewsCount();// 头部据的数量

        onInnerItemClick(position);
    }

    /**
     * 条目的点击事件
     *
     * @param position
     */
    protected void onInnerItemClick(int position) {
    }

    ;

    // 条目数量
    @Override
    public int getCount() {
        return datas.size() + 1;//增加一个 加载更多的条目
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回listview 多种行视图  种类数量
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    /**
     * 根据position返回条目对应的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {  // 0  ,1    3  (0,1,2)
        if (position == getCount() - 1) {
            //最后一个条目
            return ITEM_MORE;//加载更多类型
        }
        return getInnerItemViewType(position);
    }

    protected int getInnerItemViewType(int position) {
        return ITEM_DEFAULT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        switch (getItemViewType(position)) {
            case ITEM_MORE://加载更多
                if (convertView == null) {
                    holder = getMoreHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }

                break;
            default:// TODO 坑
                if (convertView == null) {
                    holder = getHolder(); // 1.initView 初始化
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }

                T t = datas.get(position);
                holder.setData(t); //  2. 记录数据--->refreshView 真正的显示到界面上
                break;
        }

        mDisplayHolders.add(holder);

        //调用MoreHolder的getContentView 就会显示 加载更多的条目
        return holder.getContentView(); //3.返回view对象
    }

    private BaseHolder getMoreHolder() {
        if (moreHolder == null)
            moreHolder = new MoreHolder(this, hasMore());
        return moreHolder;
    }

    /**
     * 是否有更多数据
     *
     * @return
     */
    protected boolean hasMore() {
        return true;
    }

    /**
     * 加载更多数据(1.请求服务器)
     */
    public void onload() {
        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                final List<T> newDatas = loadMore();

                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newDatas == null) {
                            // 请求失败
                            moreHolder.setData(MoreHolder.ERROR); // 自动调用refreshView
                        } else {
                            if (newDatas.size() == 0) {
                                // 没有更多数据
                                moreHolder.setData(MoreHolder.HAS_NO_MORE); // 自动调用refreshView
                            } else {
                                moreHolder.setData(MoreHolder.HAS_MORE); // 自动调用refreshView
                                //请求成功 添加到原来的集合中 更新adapter
                                datas.addAll(newDatas);
                                notifyDataSetChanged();//更新ui
                            }
                        }
                    }
                });


            }
        });
    }

    /**
     * 加载更多数据
     *
     * @return
     */
    protected List<T> loadMore() {
        return null;
    }

    /**
     * 获取 viewholder对象
     *
     * @return
     */
    protected abstract BaseHolder<T> getHolder();

    @Override
    public void onMovedToScrapHeap(View view) {
        if (null != view) {
            Object tag = view.getTag();
            if (tag instanceof BaseHolder) {
                BaseHolder holder = (BaseHolder) tag;
                synchronized (mDisplayHolders) {
                    mDisplayHolders.remove(holder);
                }
                holder.recycle();
            }
        }
    }
}


