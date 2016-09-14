package itcast.zz16.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.CyclerViewPager;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file HomePictureHolder
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class HomePictureHolder extends BaseHolder<List<String>> {

    private ViewPager viewPager;

    @Override
    protected View initView() {
        FrameLayout frameLayout = new FrameLayout(UIUtils.getContext());
        viewPager = new CyclerViewPager(UIUtils.getContext());
        frameLayout.addView(viewPager, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getDimens(R.dimen.list_header_height)));//px
        return frameLayout;
    }

    @Override
    protected void refreshView(final List<String> urls) {
        viewPager.setAdapter(new PagerAdapter() {

            // viewpager页面数量
            @Override
            public int getCount() {
                return urls.size(); // [ABCD] --->[DABCDA]
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(UIUtils.getContext());
                container.addView(imageView);

                //Picasso加载图片
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + urls.get(position))
                        .into(imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }
}
