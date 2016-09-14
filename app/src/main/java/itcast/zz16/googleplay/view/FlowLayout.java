package itcast.zz16.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itcast.zz16.googleplay.utils.UIUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file FlowLayout
 * @create_time 2016/8/27 0027
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class FlowLayout extends ViewGroup {

    private static final int HORIZONTAL_SPACING = UIUtils.dip2px(13);
    private static final int VERTICAL_SPACING = UIUtils.dip2px(13);
    private List<Line> lines = new ArrayList<>();
    private Line currentLine;//当前行
    private int useWidth;//当前行使用的宽度

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量view  父view 是有义务测量所有的子view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        currentLine = null;
        useWidth = 0;

        //1. 如果父view 的 模式 是 精确模式 ,那么子view 一定是包裹模式+ size( 父view 的大小)
        // 2. 如果父view的模式不是精确模式,那么子view 的测量规则和父view 规则保持一致

        //widthMeasureSpec mode + size 模式 + 大小
//        MeasureSpec.EXACTLY;//精确模式 match_parent  100dp
//        MeasureSpec.AT_MOST;// 包裹模式 wrap_cotent
//        MeasureSpec.UNSPECIFIED;//未指定
        //获取宽的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec)- getPaddingLeft() - getPaddingRight();
//        int childWidthMode;//子view的宽的模式
//        if (widthMode == MeasureSpec.EXACTLY){
//            childWidthMode = MeasureSpec.AT_MOST;
//        }else{
//            childWidthMode = widthMode;
//        }
        //获取子view 宽的模式
        int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;
        // 获取高的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec)- getPaddingTop() - getPaddingBottom();
        //获取子view 高的模式
        int childheightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;
        //制定子view 的测量规则
        //指定宽的测量规则
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, childWidthMode);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, childheightMode);

        //测量子view
        currentLine = new Line();//创建一行
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            //读宽还有高
            int childWidth = child.getMeasuredWidth();
//            int childHeight = child.getMeasuredHeight();
            useWidth += childWidth;
            if (useWidth <= width) {
                //d当前行使用的宽度小于父view 的宽度
                currentLine.addView(child);
                useWidth += HORIZONTAL_SPACING;
                if (useWidth > width) {
                    //换行
                    newLine();
                }
            } else {
                //换行
                newLine();
                useWidth += childWidth;
                currentLine.addView(child);
            }
        }

        if (!lines.contains(currentLine)) {
            lines.add(currentLine);//最后一行
        }

        int totalHeight = 0;
        //计算总的高度
        for (int i = 0; i < lines.size(); i++) {
            //获取每行的高度
            Line line = lines.get(i);
            int lineHeight = line.getHeight();
            totalHeight += lineHeight;
        }
        totalHeight += (lines.size() - 1) * VERTICAL_SPACING;

        //存储当前view  测量过的宽高
        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), totalHeight+getPaddingTop() + getPaddingBottom());
    }

    /**
     * 换行
     */
    private void newLine() {
        lines.add(currentLine);
        currentLine = new Line();
        useWidth = 0;
    }

    /**
     * 放置子view
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l += getPaddingLeft();
        t += getPaddingTop();
        for (Line line : lines) {
            line.layout(l, t);
            t += line.getHeight();
            t += VERTICAL_SPACING;
        }
    }

    class Line {
        private List<View> children = new ArrayList<>();
        private int height;
        private int width;//当前行的行宽

        public void addView(View child) {
            children.add(child);
            if (height < child.getMeasuredHeight()) {
                height = child.getMeasuredHeight();
            }
            width += child.getMeasuredWidth();
        }

        /**
         * 获取行高
         *
         * @return
         */
        public int getHeight() {
            return height;
        }

        /**
         * 放置子view 的位置
         *
         * @param l
         * @param t
         */
        public void layout(int l, int t) {
            //获取剩余空间宽度
            int ramaind = getMeasuredWidth() - getPaddingLeft() -getPaddingRight() - width - (children.size() - 1) * HORIZONTAL_SPACING;
            //平均分
            int r = ramaind / children.size();
            for (int i = 0; i < children.size(); i++) {
                View view = children.get(i);
                view.layout(l, t, l + view.getMeasuredWidth() + r, t + view.getMeasuredHeight());
                l += view.getMeasuredWidth();
                l += HORIZONTAL_SPACING;
                l += r;
            }
        }
    }
}
