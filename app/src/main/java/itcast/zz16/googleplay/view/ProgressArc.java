package itcast.zz16.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import itcast.zz16.googleplay.utils.UIUtils;

public class ProgressArc extends View {
    public final static int PROGRESS_STYLE_NO_PROGRESS = -1;
    public final static int PROGRESS_STYLE_DOWNLOADING = 0;
    public final static int PROGRESS_STYLE_WAITING = 1;
    private final static int START_PROGRESS = -90;
    private static final float RATIO = 360;


    private Paint mPaint;
    private RectF mArcRect;// 用于画圆形的区域
    private boolean mUserCenter;  // 扇形是否包含圆心
    private Drawable mDrawableForegroud; // 显示的图片
    private int mDrawableForegroudResId;//
    private int mArcDiameter;//圆的直径
    private int mProgressColor;// 进度的颜色
    private int mStyle = PROGRESS_STYLE_NO_PROGRESS;
    private float mProgress;
    //private float mCurrentProgress;
    //private float mStartProgress;// 动画的起始进度
    private float mSweep = 0;


    public void setStyle(int mStyle) {
        this.mStyle = mStyle;
        if (mStyle == PROGRESS_STYLE_WAITING) {
            invalidateSafe();
        }
    }

    public ProgressArc(Context context) {
        super(context);
        int strokeWidth = UIUtils.dip2px(1);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);

        mUserCenter = false;

        mArcRect = new RectF();

    }

    public void seForegroundResource(int resId) {
        if (mDrawableForegroudResId == resId) {
            return;
        }
        mDrawableForegroudResId = resId;
        mDrawableForegroud = UIUtils.getDrawable(mDrawableForegroudResId);
        invalidateSafe();  //刷新界面
    }

    private void invalidateSafe() {
        if (Thread.currentThread().getId() == 0) {
            postInvalidate();
        } else {
            invalidate();
        }
    }

    /**
     * 设置直径
     */
    public void setArcDiameter(int diameter) {
        mArcDiameter = diameter;
    }

    /**
     * 设置进度条的颜色
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mPaint.setColor(progressColor);
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {//如果是精确的
            width = widthSize;
        } else {//采用图片的大小
            width = mDrawableForegroud == null ? 0 : mDrawableForegroud.getIntrinsicWidth();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {//如果是精确的
            height = heightSize;
        } else {//采用图片的大小
            height = mDrawableForegroud == null ? 0 : mDrawableForegroud.getIntrinsicHeight();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        //计算出进度条的区域
        mArcRect.left = (width - mArcDiameter) * 0.5f;
        mArcRect.top = (height - mArcDiameter) * 0.5f;
        mArcRect.right = (width + mArcDiameter) * 0.5f;
        mArcRect.bottom = (height + mArcDiameter) * 0.5f;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawableForegroud != null) {
            // 设置边距
            mDrawableForegroud.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            mDrawableForegroud.draw(canvas);
        }
        //绘制进度
        drawArc(canvas);
    }

    public void setProgress(float progress, boolean smooth) {
        mProgress = progress;
//		if(mProgress==0){
//			mCurrentProgress=0;// 起始进度
//		}
//		mStartProgress=mCurrentProgress;
        invalidateSafe();
    }


    private void drawArc(Canvas canvas) {
        if (mStyle == PROGRESS_STYLE_DOWNLOADING || mStyle == PROGRESS_STYLE_WAITING) {
            mPaint.setColor(mProgressColor);
            mSweep = mProgress * RATIO;
            // 绘制的区域
            canvas.drawArc(mArcRect, START_PROGRESS, mSweep, mUserCenter, mPaint);

        }
    }
}