package itcast.zz16.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.CategoryInfo;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.ToastUtils;
import itcast.zz16.googleplay.utils.UIUtils;

public class CategoryContentHolder extends BaseHolder<CategoryInfo> {
    private ImageView[] ivs;
    private TextView[] tvs;
    private RelativeLayout[] rls;


    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_category_content);

        ivs = new ImageView[3];
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);

        tvs = new TextView[3];
        tvs[0] = (TextView) view.findViewById(R.id.tv_1);
        tvs[1] = (TextView) view.findViewById(R.id.tv_2);
        tvs[2] = (TextView) view.findViewById(R.id.tv_3);

        rls = new RelativeLayout[3];
        rls[0] = (RelativeLayout) view.findViewById(R.id.rl_1);
        rls[1] = (RelativeLayout) view.findViewById(R.id.rl_2);
        rls[2] = (RelativeLayout) view.findViewById(R.id.rl_3);
        return view;//创建view对象
    }

    @Override
    protected void refreshView(final CategoryInfo categoryInfo) {
        //将数据显示到对应的控件上

        //0,1,2
        if (!TextUtils.isEmpty(categoryInfo.name1) && !TextUtils.isEmpty(categoryInfo.url1)) {
            //设置数据
            Picasso
                    .with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url1)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivs[0]);
            tvs[0].setText(categoryInfo.name1);
            rls[0].setVisibility(View.VISIBLE);
            rls[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(categoryInfo.name1);
                }
            });
        } else {
            //隐藏
            rls[0].setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(categoryInfo.name2) && !TextUtils.isEmpty(categoryInfo.url2)) {
            //设置数据
            Picasso
                    .with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url2)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivs[1]);
            tvs[1].setText(categoryInfo.name2);
            rls[1].setVisibility(View.VISIBLE);
            rls[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(categoryInfo.name2);
                }
            });
        } else {
            //隐藏
            rls[1].setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(categoryInfo.name3) && !TextUtils.isEmpty(categoryInfo.url3)) {
            //设置数据
            Picasso
                    .with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url3)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivs[2]);
            tvs[2].setText(categoryInfo.name3);
            rls[2].setVisibility(View.VISIBLE);
            rls[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(categoryInfo.name3);
                }
            });
        } else {
            //隐藏
            rls[2].setVisibility(View.INVISIBLE);
        }
    }
}