package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

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
 * @file DetailScreenHolder
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class DetailScreenHolder  extends BaseHolder<AppInfo>{
    private ImageView [] ivs;//截图imageview
    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_screen);

        ivs = new ImageView[5];//最多五张图片
        ivs[0] = (ImageView) view.findViewById(R.id.screen_1);
        ivs[1] = (ImageView) view.findViewById(R.id.screen_2);
        ivs[2] = (ImageView) view.findViewById(R.id.screen_3);
        ivs[3] = (ImageView) view.findViewById(R.id.screen_4);
        ivs[4] = (ImageView) view.findViewById(R.id.screen_5);

        return view;
    }

    @Override
    protected void refreshView(AppInfo appInfo) {
        List<String> screens = appInfo.screens;//截图的url   4

        for (int i = 0; i < 5; i++) {
            //   0,1,2,3,4
            if (i < screens.size()){
                //
                Picasso
                        .with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + screens.get(i))
                        .into(ivs[i]);
            }else{
                //隐藏
                ivs[i].setVisibility(View.GONE);
            }
        }

    }
}
