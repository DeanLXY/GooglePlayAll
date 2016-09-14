package itcast.zz16.googleplay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.Random;

import itcast.zz16.googleplay.R;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file RatioLayout
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * 具有宽高比例的控件
 */
public class RatioLayout extends FrameLayout {
    float ratio =  2.43f;// 宽/高

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    // new
    public RatioLayout(Context context) {
       this(context,null);
    }

    //在布局文件中声明的时候调用
    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio,ratio);
        typedArray.recycle();//释放资源


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        widthMeasureSpec  宽的测量规则  模式 + 大小
        //heightMeasureSpec 高的测量规则
        //  指定高的测量规则
        // 如果宽是精确模式  高不是精确模式 ,使用宽计算高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽的模式
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽的值
          int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高的模式
        int height = MeasureSpec.getSize(heightMeasureSpec);//获取宽的值
        if (widthMode == MeasureSpec.EXACTLY&& heightMode != MeasureSpec.EXACTLY){
            height = (int) (width * 1.0f/ ratio + 0.5f);  //    12.2   12  12.6  13
        }else if(heightMode== MeasureSpec.EXACTLY&& widthMode != MeasureSpec.EXACTLY){
            //高是精确模式  宽不是精确模式   使用高计算宽
            width = (int) (height * ratio + 0.5f);
        }


        // 制定高的测量规则
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        //宽的测量规则
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
