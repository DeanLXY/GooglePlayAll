package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.CategoryInfo;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file CategoryTitleHolder
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

    private TextView textView;

    @Override
    protected View initView() {
        textView = new TextView(UIUtils.getContext());
        textView.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.grid_item_bg));
//        textView.setBackground(UIUtils.getDrawable(R.drawable.grid_item_bg));
        return textView;
    }

    @Override
    protected void refreshView(CategoryInfo categoryInfo) {
        textView.setText(categoryInfo.title);
    }
}
