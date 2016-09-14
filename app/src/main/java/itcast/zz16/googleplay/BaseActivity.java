package itcast.zz16.googleplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseActivity
 * @create_time 2016/8/20 0020
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 * 1. 将相同的方法抽取出来
 * 2. 统一管理所有的acitivity
 */
public class BaseActivity extends AppCompatActivity {

    // 共享资源
    public static List<BaseActivity> activities = new ArrayList<>();
    public  static BaseActivity activity;//当前的Activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加到集合中统一维护
        synchronized (activities) {
            activities.add(this);
        }

        init();
        initViews();
        initToolBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (activities) {
            activities.remove(this);
        }
    }


    public void killAll() {
        //遍历中不允许增删
        //1. 复制一份
        //2. CopyOnWriteArrayList 可以在遍历中做增删操作
        List<BaseActivity> copy;
        synchronized (activities) {
            copy = new ArrayList<>(activities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }

        //  自杀进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 初始化操作
     */
    protected void init() {

    }

    /**
     * 初始化所有的控件
     */
    protected void initViews() {

    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {

    }
}
