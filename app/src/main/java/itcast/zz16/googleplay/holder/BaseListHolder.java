package itcast.zz16.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.UIUtils;

public class BaseListHolder extends BaseHolder<AppInfo> {
        public ImageView item_icon;
        public TextView action_txt;
        public TextView item_title;
        public RatingBar item_rating;
        public TextView item_size;
        public TextView item_bottom;


        @Override
        protected View initView() {
            View contentView = UIUtils.inflate(R.layout.item_appinfo);
            this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
            this.action_txt = (TextView) contentView.findViewById(R.id.action_txt);
            this.item_title = (TextView) contentView.findViewById(R.id.item_title);
            this.item_rating = (RatingBar) contentView.findViewById(R.id.item_rating);
            this.item_size = (TextView) contentView.findViewById(R.id.item_size);
            this.item_bottom = (TextView) contentView.findViewById(R.id.item_bottom);
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
        }
    }