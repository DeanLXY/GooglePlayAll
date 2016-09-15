package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.db.DownloadDbHelper;
import itcast.zz16.googleplay.db.DownloadInfo;
import itcast.zz16.googleplay.manager.DownloadManager;
import itcast.zz16.googleplay.utils.ThreadUtils;
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
 * <p>
 * ======================
 */
public class DetailBottomHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver {
    @Bind(R.id.bottom_favorites)
    Button bottomFavorites;
    @Bind(R.id.bottom_share)
    Button bottomShare;
    @Bind(R.id.progress_btn)
    Button progressBtn;
    @Bind(R.id.pb_load_process)
    ProgressBar pbLoadProcess;
    @Bind(R.id.tv_load_process)
    TextView tvLoadProcess;
    @Bind(R.id.progress_layout)
    FrameLayout progressLayout;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    private int mState; // 下载状态
    private int mProgress; // 当前进度

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_bottom);

        // view注入
        ButterKnife.bind(this, view);
        startObserver();
        return view;//view对象
    }

    @Override
    protected void refreshView(AppInfo appInfo) {
//        startObserver();
        if (DownloadDbHelper.getInstance().getDownloadInfo(appInfo.id) != null) {
            mState = DownloadDbHelper.getInstance().getDownloadInfo(appInfo.id).getDownloadState();
            mProgress = DownloadDbHelper.getInstance().getDownloadInfo(appInfo.id).getCurrentSize().intValue();
        }
        refreshState(mState, mProgress);
    }

    public void refreshState(int state, int progress) {
        mState = state;
        mProgress = progress;
        switch (mState) {
            case DownloadManager.STATE_NONE:
                progressLayout.setVisibility(View.GONE);
                progressBtn.setVisibility(View.VISIBLE);
                progressBtn
                        .setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_PAUSE:
                progressLayout.setVisibility(View.VISIBLE);
                pbLoadProcess.setVisibility(View.VISIBLE);
                pbLoadProcess.setProgress(progress);
                progressBtn.setVisibility(View.GONE);
                tvLoadProcess.setVisibility(View.VISIBLE);
                tvLoadProcess.setText(UIUtils.getString(R.string.app_state_paused));
                break;
            case DownloadManager.STATE_ERROR:
                progressLayout.setVisibility(View.GONE);
                tvLoadProcess.setVisibility(View.GONE);
                progressBtn.setVisibility(View.VISIBLE);
                progressBtn.setText(R.string.app_state_error);
                break;
            case DownloadManager.STATE_WAITING:
                progressLayout.setVisibility(View.VISIBLE);
                pbLoadProcess.setVisibility(View.VISIBLE);
                pbLoadProcess.setProgress(progress);

                tvLoadProcess.setVisibility(View.VISIBLE);
                tvLoadProcess.setText(UIUtils.getString(R.string.app_state_waiting));
                progressBtn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                progressLayout.setVisibility(View.VISIBLE);
                pbLoadProcess.setVisibility(View.VISIBLE);
                pbLoadProcess.setProgress(progress);
                tvLoadProcess.setVisibility(View.VISIBLE);
                tvLoadProcess.setText(progress + "%");
                // mProgeressView.setCenterText("");
                progressBtn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOED:
                progressLayout.setVisibility(View.GONE);
                tvLoadProcess.setVisibility(View.GONE);
                progressBtn.setVisibility(View.VISIBLE);
                progressBtn.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.bottom_favorites, R.id.bottom_share, R.id.progress_btn, R.id.progress_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_favorites:
                ToastUtils.showToast("收藏");
                break;
            case R.id.bottom_share:
                ToastUtils.showToast("分享");
                break;
            case R.id.progress_layout:
            case R.id.progress_btn:
//                ToastUtils.showToast("下载");
                if (mState == DownloadManager.STATE_DOWNLOADING) {
                    DownloadManager.getInstance().pause(data);
                } else if (mState == DownloadManager.STATE_DOWNLOED) {
                    DownloadManager.getInstance().install(data);
                } else {
                    DownloadManager.getInstance().addDownloadTask(data);
                }
                break;
        }
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    /**
     * 下载状态改变
     */
    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    /**
     * 下载进度改变
     */
    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {

        //判断时候是当前任务
        if (data.id == info.getId()) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshState(info.getDownloadState(), (int) (info
                            .getCurrentSize() * 100 / info.getAppSize()));
                }
            });
        }
    }
}
