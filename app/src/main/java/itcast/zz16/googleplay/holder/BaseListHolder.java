package itcast.zz16.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.db.DownloadDbHelper;
import itcast.zz16.googleplay.db.DownloadInfo;
import itcast.zz16.googleplay.manager.DownloadManager;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.ThreadUtils;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.ProgressArc;

public class BaseListHolder extends BaseHolder<AppInfo> {
    public ImageView item_icon;
    public TextView action_txt;
    public TextView item_title;
    public RatingBar item_rating;
    public TextView item_size;
    public TextView item_bottom;
    private ProgressArc mProgressArc;
    private FrameLayout progress_layout;
    private int mState;
    private float mProgress;


   /* */

    @Override
    protected View initView() {
        View contentView = UIUtils.inflate(R.layout.item_appinfo);
        this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
        this.action_txt = (TextView) contentView.findViewById(R.id.action_txt);
        this.item_title = (TextView) contentView.findViewById(R.id.item_title);
        this.item_rating = (RatingBar) contentView.findViewById(R.id.item_rating);
        this.item_size = (TextView) contentView.findViewById(R.id.item_size);
        this.item_bottom = (TextView) contentView.findViewById(R.id.item_bottom);
        progress_layout = (FrameLayout) contentView.findViewById(R.id.action_progress);

        mProgressArc = new ProgressArc(UIUtils.getContext());
        int arcDiameter = UIUtils.dip2px(26);
        // 设置圆的直径
        mProgressArc.setArcDiameter(arcDiameter);
        // 设置进度条的颜色

        mProgressArc.setProgressColor(UIUtils.getColor(R.color.progress));
        int size = UIUtils.dip2px(27);
        progress_layout.addView(mProgressArc, new ViewGroup.LayoutParams(size,
                size));

        return contentView;
    }


    /**
     * 将数据显示到对应的控件上
     *
     * @param appInfo
     */
    @Override
    protected void refreshView(AppInfo appInfo) {
        this.item_title.setText(appInfo.name);
        this.item_rating.setRating((float) appInfo.stars);
        this.item_size.setText(Formatter.formatFileSize(UIUtils.getContext(), appInfo.size));// long -   xxMB
        this.item_bottom.setText(appInfo.des);

        // 使用picasso加载图片
//            "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
        Picasso.
                with(UIUtils.getContext())
                .load(HttpHelper.BASEURL + "/image?name=" + appInfo.iconUrl)
                .into(this.item_icon);
        DownloadInfo info = DownloadDbHelper.getInstance().getDownloadInfo(appInfo.id);
        if (info != null) {
            mState = info.getDownloadState();
            mProgress = (int) (info.getCurrentSize().intValue()
                    * 100 / info.getAppSize());
        }
        refreshState(mState, mProgress);
    }

    public void refreshState(int state, float progress) {
        mState = state;
        mProgress = progress;
        switch (mState) {
            case DownloadManager.STATE_NONE:
                mProgressArc.seForegroundResource(R.mipmap.ic_download);
                // 是否画进度条，不画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(R.string.app_state_download);
                break;
            case DownloadManager.STATE_PAUSE:
                mProgressArc.seForegroundResource(R.mipmap.ic_resume);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(R.string.app_state_paused);
                break;
            case DownloadManager.STATE_ERROR:
                mProgressArc.seForegroundResource(R.mipmap.ic_redownload);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(R.string.app_state_error);
                break;
            case DownloadManager.STATE_WAITING:
                mProgressArc.seForegroundResource(R.mipmap.ic_pause);
                // 是否画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                mProgressArc.setProgress(progress / 100, false);
                action_txt.setText(R.string.app_state_waiting);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgressArc.seForegroundResource(R.mipmap.ic_pause);
                // 画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                mProgressArc.setProgress(progress / 100, true);
                action_txt.setText(mProgress + "%");
                break;
            case DownloadManager.STATE_DOWNLOED:
                mProgressArc.seForegroundResource(R.mipmap.ic_install);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }
}