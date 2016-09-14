package itcast.zz16.googleplay.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file CyclerViewPager
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class CyclerViewPager extends ViewPager {
    public CyclerViewPager(Context context) {
        super(context);
    }

    public CyclerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        InnerOnPageChangeListener innerOnPageChangeListener = new InnerOnPageChangeListener(listener);
        super.addOnPageChangeListener(innerOnPageChangeListener);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        // 包装
        InnerAdapter innerAdapter = new InnerAdapter(adapter);
        //监听页面的切换
        addOnPageChangeListener(null);
        //使用包装过的adapter
        super.setAdapter(innerAdapter);
        setCurrentItem(1);

        startScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下停止轮播
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL://手指事件取消  从外边抬起的时候取消事件
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
        }


        return super.onTouchEvent(ev);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = getCurrentItem();
            currentItem ++;
            setCurrentItem(currentItem);
            handler.sendEmptyMessageDelayed(1, 4000);
        }
    };

    public void startScroll() {
        //启动轮播
        handler.sendEmptyMessageDelayed(1, 4000);
    }

    /**
     * 停止轮播
     */
    public void stopScroll(){
        handler.removeMessages(1);
    }


    class InnerOnPageChangeListener implements OnPageChangeListener {

        private int position;
        private OnPageChangeListener listener;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {

            this.listener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (listener != null) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {

            this.position = position;
            if (listener != null) {
                listener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                //空闲状态

                // 是否切换到最后一个A元素
                if (position == getAdapter().getCount() - 1) {
                    CyclerViewPager.this.setCurrentItem(1, false);
                } else if (position == 0) {
                    //最前边的D元素
                    CyclerViewPager.this.setCurrentItem(CyclerViewPager.this.getAdapter().getCount() - 2, false);

                }
            }

            if (listener != null) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

    class InnerAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public InnerAdapter(PagerAdapter adapter) {

            this.adapter = adapter;
        }

        // viewpager页面数量
        @Override
        public int getCount() {
            return adapter.getCount() + 2;//adapter [ABCD] + 2
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return adapter.isViewFromObject(view, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //position  [DABCDA]的索引
            //找到 在原来集合中的索引
            if (position == 0) {
                //第一个D元素
                position = adapter.getCount() - 1;
            } else if (position == getCount() - 1) {
                //最后一个A元素
                position = 0;
            } else {
                position -= 1; //其他元素的索引
            }


            // position已经 修正过
            return adapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem(container, position, object);
        }

    }
}
