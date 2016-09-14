package itcast.zz16.googleplay.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DetailDesHolder
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DetailDesHolder extends BaseHolder<AppInfo> implements View.OnClickListener {

    boolean isOpen = false;//默认关闭状态
    private TextView des_content;//描述信息
    private TextView des_author;//作者信息
    private ImageView des_arrow;//箭头
    private RelativeLayout des_layout;//根元素

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_des);
        des_content = (TextView) view.findViewById(R.id.des_content);
        des_author = (TextView) view.findViewById(R.id.des_author);
        des_arrow = (ImageView) view.findViewById(R.id.des_arrow);
        des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);

        des_layout.setOnClickListener(this);


        //模式7行高度
        ViewGroup.LayoutParams lp = des_content.getLayoutParams();
        lp.height = getShortMeasuredHeight();

        // 重新测量  重新绘制
        des_content.requestLayout();
        return view;//创建view对象 初始化操作 (findviewById)
    }

    @Override
    protected void refreshView(AppInfo appInfo) {
        //拿着数据显示到对应的控件上
        des_content.setText(appInfo.des);
        des_author.setText(String.format(UIUtils.getString(R.string.txt_author), appInfo.author));
    }

    @Override
    public void onClick(View v) {
        final ScrollView scrollView  = getScrollView(v);
//        scrollView.scrollTo(0,scrollView.getMeasuredHeight());
        int startHeight;//开始值
        int targetHeight;//目标值
        if (isOpen) {
            //当前是打开 点击后关闭
            isOpen = false;
//            des_content.setVisibility(View.GONE);
            startHeight = getLongMeasuredHeight();
            targetHeight = getShortMeasuredHeight();//7行的高度
        } else {
            isOpen = true;
//            des_content.setVisibility(View.VISIBLE);
            startHeight = getShortMeasuredHeight();
            targetHeight = getLongMeasuredHeight();
            //滚动到底部
            scrollView.scrollTo(0,scrollView.getMeasuredHeight());
        }


        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight,targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newHeight = (int) valueAnimator.getAnimatedValue();
                // 重新设置高度
                ViewGroup.LayoutParams lp = des_content.getLayoutParams();
                lp.height = newHeight;

                // 重新测量  重新绘制
                des_content.requestLayout();
                //滚动到底部
                scrollView.scrollTo(0,scrollView.getMeasuredHeight());

            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isOpen){
                    //结束后是打开状态
                    des_arrow.setImageResource(R.mipmap.arrow_up);
                }else{
                    des_arrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(600);
        valueAnimator.start();
    }

    /**
     *
     * 方法执行 是 入栈(栈内存溢出)/出栈
     * 1.循环 2.尾递归调用(返回的公式 不增长)
     * 1. fib 2.八皇后问题
     * 获取scrollview
     * @param v
     * @return
     */
    private ScrollView getScrollView(View v) {
        if ( v!= null){
            ViewParent parent = v.getParent();
            if (parent != null && parent instanceof  ScrollView){
                return (ScrollView) parent;
            }else{
                return getScrollView((View) parent);
            }
            
        }
        return null;
    }

    /**
     * 获取7行的testview
     * 创建一个 7行的textview 字体要和原来textview 大小保持一致 14dp
     *
     * @return
     */
    private int getShortMeasuredHeight() {
        TextView textView = new TextView(UIUtils.getContext());
        textView.setLines(7);//7行
        //  dp ___>px
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);// 14dp

        int width = des_content.getMeasuredWidth();//获取控件的宽度
        //预测量 测量7行高度
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //制定高的测量规则 模式是包裹内容 size 表示最大不超过的大小
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);//包裹内容
        //测量控件
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 获取最高高度  预测量
     *
     * @return
     */
    private int getLongMeasuredHeight() {
// widthMeasureSpec mode + size  精确模式  包裹模式 未指定模式
        //制定宽的尺子
        int width = des_content.getMeasuredWidth();//获取控件的宽度
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //制定高的测量规则 模式是包裹内容 size 表示最大不超过的大小
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);//包裹内容
        //测量控件
        des_content.measure(widthMeasureSpec, heightMeasureSpec);
        //从尺子上读取 测量结果
        return des_content.getMeasuredHeight();
    }
}
