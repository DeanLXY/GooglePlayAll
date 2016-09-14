package itcast.zz16.googleplay.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import itcast.zz16.googleplay.R;
import itcast.zz16.googleplay.adapter.DefaultAdapter;
import itcast.zz16.googleplay.bean.SubjectInfo;
import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.protocol.SubjectProtocol;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.BaseListView;

/**
 * A simple {@link Fragment} subclass.
 * 专题
 */
public class SubjectFragment extends BaseFragment {


    private List<SubjectInfo> datas;

    @Override
    protected int load() {
        // 1. 请求数据2.缓存 3.复用缓存 4解析
        SubjectProtocol protocol = new SubjectProtocol();
        datas = protocol.load(0);
        return checkDatas(datas);//LoadingPage.STATU_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new SubjectAdapter(datas,listView));
        return listView;
    }


    class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas,ListView listView) {
            super(datas,listView);
        }

        @Override
        protected BaseHolder<SubjectInfo> getHolder() {
            return new SubjectViewHolder();
        }

        @Override
        protected List<SubjectInfo> loadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
           List<SubjectInfo> newDatas = protocol.load(datas.size());
            return newDatas;
        }
    }

    public class SubjectViewHolder extends BaseHolder<SubjectInfo> {

        public ImageView item_icon;
        public TextView item_txt;

        @Override
        protected View initView() {
            View contentView = UIUtils.inflate(R.layout.item_subjectinfo);
            this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
            this.item_txt = (TextView) contentView.findViewById(R.id.item_txt);
            return contentView;
        }

        /**
         * 自动将数据显示到对应的控件上
         *
         * @param subjectInfo
         */
        @Override
        protected void refreshView(SubjectInfo subjectInfo) {
            this.item_txt.setText(subjectInfo.des);

            Picasso.with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + subjectInfo.url)
                    .into(this.item_icon);
        }
    }

}
