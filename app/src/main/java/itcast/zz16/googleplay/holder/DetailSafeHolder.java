package itcast.zz16.googleplay.holder;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.squareup.picasso.Picasso;

import java.util.List;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.LogUtil;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DetailSafeHolder
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    boolean isOpen = false;//默认关闭
    private ImageView[] ivs;//安全扫描结果的imageview
    private ImageView[] ivdess;//安全扫描结果描述imageview
    private TextView[] tvdess;//安全扫描结果描述TextView
    private ImageView ivArrow;//箭头
    private LinearLayout[] llDes;//扫描描述 条目布局
    private RelativeLayout safe_layout;//根元素
    private LinearLayout safe_content;//扫描描述的根元素

    @Override
    protected View initView() {
        // 创建view对象
        View view = UIUtils.inflate(R.layout.holder_detail_safe);

        ivs = new ImageView[4];// 最多有四个imageview
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
        ivs[3] = (ImageView) view.findViewById(R.id.iv_4);

        ivdess = new ImageView[4];
        ivdess[0] = (ImageView) view.findViewById(R.id.des_iv_1);
        ivdess[1] = (ImageView) view.findViewById(R.id.des_iv_2);
        ivdess[2] = (ImageView) view.findViewById(R.id.des_iv_3);
        ivdess[3] = (ImageView) view.findViewById(R.id.des_iv_4);

        tvdess = new TextView[4];
        tvdess[0] = (TextView) view.findViewById(R.id.des_tv_1);
        tvdess[1] = (TextView) view.findViewById(R.id.des_tv_2);
        tvdess[2] = (TextView) view.findViewById(R.id.des_tv_3);
        tvdess[3] = (TextView) view.findViewById(R.id.des_tv_4);

        ivArrow = (ImageView) view.findViewById(R.id.safe_arrow);

        llDes = new LinearLayout[4];
        llDes[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
        llDes[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
        llDes[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
        llDes[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);


        safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
        safe_content = (LinearLayout) view.findViewById(R.id.safe_content);
        safe_layout.setOnClickListener(this);
//        safe_layout.setSystemUiVisibility();
        ViewGroup.LayoutParams lp = safe_content.getLayoutParams();
        lp.height = 0;
        safe_content.requestLayout();
        return view;
    }

    @Override
    protected void refreshView(AppInfo appInfo) {
        List<String> safeUrls = appInfo.safeUrls;
        List<String> safeDesUrls = appInfo.safeDesUrls;
        List<String> safeDeses = appInfo.safeDeses;
        List<Integer> safeDesColors = appInfo.safeDesColors;
        for (int i = 0; i < 4; i++) {
            // 0,1,2,3
            if (i < safeUrls.size() && i < safeDesUrls.size() && i < safeDeses.size() && i < safeDesColors.size()) {
                // 0,1,2
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + safeUrls.get(i))
                        .into(ivs[i]);
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + safeDesUrls.get(i))
                        .into(ivdess[i]);

                tvdess[i].setText(safeDeses.get(i));
            } else {
                ivs[i].setVisibility(View.GONE);
                llDes[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int startHeight;//开始高度
        int targetHeight;//目标高度
        if (isOpen) {//当前是打开状态
            startHeight = getMeasuredHeight();//获取高度
            targetHeight = 0;
            //关闭
            isOpen = false;
//            safe_content.setVisibility(View.GONE);
        } else {//当前是关闭状态
            startHeight = 0;//
            targetHeight = getMeasuredHeight();//获取高度
            //打开
            isOpen = true;
//            safe_content.setVisibility(View.VISIBLE);
        }
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LogUtil.d("%s", "value = " + height);
                ViewGroup.LayoutParams lp = safe_content.getLayoutParams();
                lp.height = height;
                // 重新测量 重新绘制
//                safe_content.setLayoutParams(lp);
                //重新测量 重新绘制
                safe_content.requestLayout();
            }
        });

        // slidingmenu   nineoldandroids
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpen) {
                    ivArrow.setImageResource(R.mipmap.arrow_up);

                } else {
                    ivArrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.setDuration(500);
        animator.start();
    }

    /**
     * 预测量
     * measure 是真正测量的方法 onmeasure
     *
     * @return
     */
    private int getMeasuredHeight() {
        //widthMeasureSpec 测量规则  mode + size
        //mode
//        View.MeasureSpec.EXACTLY;// 精确模式 100dp match_parent
//        View.MeasureSpec.AT_MOST;//wrap_content
//        View.MeasureSpec.UNSPECIFIED//未指定

        //测量 控件
        int width = safe_content.getMeasuredWidth();//控件的宽度
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //指定高的尺子 规则 如果是包裹模式  size  表示最大不超过的值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);//高是包裹内容
        safe_content.measure(widthMeasureSpec, heightMeasureSpec);

        //读取控件的高度
        return safe_content.getMeasuredHeight();
    }
}
