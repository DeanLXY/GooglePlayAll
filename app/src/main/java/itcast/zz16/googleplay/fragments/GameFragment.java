package itcast.zz16.googleplay.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import itcast.zz16.googleplay.adapter.BaseListAdapter;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.protocol.GameProtocol;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.BaseListView;
import itcast.zz16.googleplay.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 * 游戏
 */
public class GameFragment extends BaseFragment {


    private List<AppInfo> datas;

    @Override
    protected int load() {
        GameProtocol protocol = new GameProtocol();
        datas = protocol.load(0);
        return checkDatas(datas);//LoadingPage.STATU_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new BaseListAdapter(datas,listView){
            @Override
            protected List<AppInfo> loadMore() {
                GameProtocol protocol = new GameProtocol();
                List<AppInfo> newDatas = protocol.load(datas.size());
                return newDatas;
            }
        });

        return listView;
    }
}
