package itcast.zz16.googleplay.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

import itcast.zz16.googleplay.R;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseListView
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        <ListView
//        android:layout_width="match_parent"
//        android:cacheColorHint="@android:color/transparent"
//        android:listSelector="@android:color/transparent"
//        android:divider="@android:color/transparent"
//        android:layout_height="match_parent"></ListView>
        //  取消低版本 滑动时的黑色渲染效果
//        0x  表示十六进制  ARGB
        setCacheColorHint(0x00000000);
        //取消默认的状态选择器
//        setSelector(R.drawable.nothing);
        setSelector(new ColorDrawable(0x00000000));
        //取消默认分割线
        setDivider(new ColorDrawable(0x00000000));

    }
}
