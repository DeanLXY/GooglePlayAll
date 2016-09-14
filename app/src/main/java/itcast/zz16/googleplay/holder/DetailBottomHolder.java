package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.ToastUtils;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DetailBottomHolder
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DetailBottomHolder extends BaseHolder<AppInfo> {
    @Bind(R.id.bottom_favorites)
    Button bottomFavorites;
    @Bind(R.id.bottom_share)
    Button bottomShare;
    @Bind(R.id.progress_btn)
    Button progressBtn;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_bottom);

        // view注入
        ButterKnife.bind(this,view);
        return view;//view对象
    }

    @Override
    protected void refreshView(AppInfo appInfo) {

    }

    @OnClick({R.id.bottom_favorites, R.id.bottom_share, R.id.progress_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_favorites:
                ToastUtils.showToast("收藏");
                break;
            case R.id.bottom_share:
                ToastUtils.showToast("分享");
                break;
            case R.id.progress_btn:
                ToastUtils.showToast("下载");
                break;
        }
    }
}
