package itcast.zz16.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.UserInfo;
import itcast.zz16.googleplay.protocol.UserProtocol;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.ThreadUtils;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file MenuHolder
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class MenuHolder extends BaseHolder<UserInfo> {
    @Bind(R.id.image_photo)
    ImageView imagePhoto;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_email)
    TextView userEmail;
    @Bind(R.id.photo_layout)
    RelativeLayout photoLayout;
    @Bind(R.id.home_layout)
    RelativeLayout homeLayout;
    @Bind(R.id.setting_layout)
    RelativeLayout settingLayout;
    @Bind(R.id.theme_layout)
    RelativeLayout themeLayout;
    @Bind(R.id.scans_layout)
    RelativeLayout scansLayout;
    @Bind(R.id.feedback_layout)
    RelativeLayout feedbackLayout;
    @Bind(R.id.updates_layout)
    RelativeLayout updatesLayout;
    @Bind(R.id.about_layout)
    RelativeLayout aboutLayout;
    @Bind(R.id.exit_layout)
    RelativeLayout exitLayout;

    @Override
    protected View initView() {
        // xml  ---View
        View view = UIUtils.inflate(R.layout.holder_menu);
        // view注入
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(UserInfo userInfo) {
        userName.setText(userInfo.name);
        userEmail.setText(userInfo.email);
        Picasso.with(UIUtils.getContext())
                .load(HttpHelper.BASEURL + "/image?name=" + userInfo.url)
                .into(imagePhoto);
    }

    @OnClick({R.id.photo_layout, R.id.home_layout, R.id.setting_layout, R.id.theme_layout, R.id.scans_layout, R.id.feedback_layout, R.id.updates_layout, R.id.about_layout, R.id.exit_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_layout:
                login();
                break;
            case R.id.home_layout:
                break;
            case R.id.setting_layout:
                break;
            case R.id.theme_layout:
                break;
            case R.id.scans_layout:
                break;
            case R.id.feedback_layout:
                break;
            case R.id.updates_layout:
                break;
            case R.id.about_layout:
                break;
            case R.id.exit_layout:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                //协议对象
                UserProtocol protocol = new UserProtocol();
                final UserInfo userInfo = protocol.load(0);

                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(userInfo);// ---->refreshView 将数据显示到对应的控件上
                    }
                });
            }
        });
    }
}
