package itcast.zz16.googleplay.fragments;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file FragmentFactory
 * @create_time 2016/8/20 0020
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 * 工厂
 * 1.静态工厂
 * 2.简单工厂模式
 * 3.抽象工厂
 */
public class FragmentFactory {
    private static Map<Integer, BaseFragment> fragents = new HashMap<>();

    /**
     * 将每次创建的Fragment对象 放在缓存中,下次再使用的时候,先去缓存中获取,缓存中存在
     * 就使用缓存中对象.     就直接创建新的对象,再次添加到缓存中
     * 根据position生产Fragment
     *
     * @param position
     * @return
     */
    public static BaseFragment create(int position) {
        BaseFragment fragment = fragents.get(position);//从缓存中获取
        if (fragment == null) { //如果缓存中没有 创建新的对象 添加到缓存中
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new CategoryFragment();
                    break;
                case 5:
                    fragment = new TopFragment();
                    break;
            }
            if (fragment != null) {
                //缓存下来
                fragents.put(position, fragment);
            }
        }
        return fragment;
    }
}
