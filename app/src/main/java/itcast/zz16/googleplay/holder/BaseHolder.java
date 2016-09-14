package itcast.zz16.googleplay.holder;

import android.view.View;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseHolder
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public abstract class BaseHolder<Data> {
    public View contentView;
    protected Data data;

    public BaseHolder() {
        this.contentView = initView();
        contentView.setTag(this);
    }

    public View getContentView() {
        return contentView;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        refreshView(data);
    }

    /**
     * 1. 创建view对象
     * 2.初始化view对象(findViewById)
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 将数据显示到对应的控件上
     */
    protected abstract void refreshView(Data data);
}
