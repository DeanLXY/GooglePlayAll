package itcast.zz16.googleplay.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.utils.ViewUtils;
import itcast.zz16.googleplay.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 * 所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 从视图中移除  一个view 只能有一个爹
        // 找到它爹
        ViewUtils.removeFromParent(loadingPage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (loadingPage == null) {
            // 最长生命周期的上下文
            loadingPage = new LoadingPage(UIUtils.getContext()) {
                @Override
                protected int load() {
                    return BaseFragment.this.load();
                }

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };

        }
//        showpage();//根据状态切换 几种不同界面的显示和隐藏
       // show();//根据服务器返回的状态 切换为不同的界面

        return loadingPage;
    }

    /**
     * 开始请求服务器数据
     */
    public void show() {
        if (loadingPage != null) {
            loadingPage.show();
        }
    }

    /**
     * 不校验集合的元素的类型  ?
     * @param datas
     * @return
     */
    public int checkDatas(List<? extends  Object> datas) {
        if (datas == null) {
            return LoadingPage.STATU_ERROR;//失败
        } else {
            if (datas.size() == 0) {
                return LoadingPage.STATU_EMPTY;//为空
            } else {
                return LoadingPage.STATU_SUCCESS;//成功
            }
        }
    }

    /**
     * 请求服务器数据 返回对应的状态
     * public static final int STATU_ERROR = 2;//失败状态
     * public static final int STATU_EMPTY = 3;//为空状态
     * public static final int STATU_SUCCESS = 4;//成功状态
     * shrit  + alt + ctrl + f7
     * @return
     */
    protected abstract int load();

    /**
     * 创建成功界面
     *
     * @return
     */
    protected abstract View createSuccessView();
}
