package itcast.zz16.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DetailInfoHolder
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DetailInfoHolder extends BaseHolder<AppInfo> {


    @Bind(R.id.item_icon)
    ImageView itemIcon;
    @Bind(R.id.item_title)
    TextView itemTitle;
    @Bind(R.id.item_rating)
    RatingBar itemRating;
    @Bind(R.id.item_download)
    TextView itemDownload;
    @Bind(R.id.item_version)
    TextView itemVersion;
    @Bind(R.id.item_date)
    TextView itemDate;
    @Bind(R.id.item_size)
    TextView itemSize;

    @Override
    protected View initView() {
        // 创建view对象
        // 初始化操作

        // xml --- View
        View view = UIUtils.inflate(R.layout.holder_detail_info);
        //没有 注入
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(AppInfo appInfo) {
        itemTitle.setText(appInfo.name);
        itemRating.setRating((float) appInfo.stars);
//        itemIcon
        Picasso.with(UIUtils.getContext())
                .load(HttpHelper.BASEURL+"/image?name="+appInfo.iconUrl)
                .placeholder(R.mipmap.ic_default)
                .into(itemIcon);

        // i18n   下载:%s textView 笔记类软件  EditText 支持图片输入  (SpannableString)
        itemDownload.setText(String.format(UIUtils.getString(R.string.txt_downloadnum),appInfo.downloadNum));//
        itemVersion.setText(String.format(UIUtils.getString(R.string.txt_verion),appInfo.version));//
        itemDate.setText(String.format(UIUtils.getString(R.string.txt_date),appInfo.date));//
        itemSize.setText(String.format(UIUtils.getString(R.string.txt_size), Formatter.formatFileSize(UIUtils.getContext(),appInfo.size)));//
    }
}
