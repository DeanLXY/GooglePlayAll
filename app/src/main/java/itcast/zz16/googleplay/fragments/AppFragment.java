package itcast.zz16.googleplay.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import itcast.zz16.googleplay.adapter.BaseListAdapter;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.protocol.AppProtocol;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.BaseListView;

/**
 * A simple {@link Fragment} subclass.
 * 应用
 */
public class AppFragment extends BaseFragment {


    private List<AppInfo> datas;

    @Override
    protected int load() {
        AppProtocol protocol = new AppProtocol();
        datas = protocol.load(0);

        return checkDatas(datas);// LoadingPage.STATU_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new BaseListAdapter(datas,listView){

            @Override
            protected List<AppInfo> loadMore() {
                AppProtocol protocol = new AppProtocol();
               List<AppInfo> newDatas = protocol.load(datas.size());
                return newDatas;
            }
        });
        return listView;
    }

}
