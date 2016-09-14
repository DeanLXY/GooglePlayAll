package itcast.zz16.googleplay.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import itcast.zz16.googleplay.adapter.DefaultAdapter;
import itcast.zz16.googleplay.bean.CategoryInfo;
import itcast.zz16.googleplay.holder.BaseHolder;
import itcast.zz16.googleplay.holder.CategoryContentHolder;
import itcast.zz16.googleplay.holder.CategoryTitleHolder;
import itcast.zz16.googleplay.protocol.CategoryProtocol;
import itcast.zz16.googleplay.utils.UIUtils;
import itcast.zz16.googleplay.view.BaseListView;

/**
 * A simple {@link Fragment} subclass.
 * 分类
 */
public class CategoryFragment extends BaseFragment {


    private static final int ITEM_TITLE = 2;//标题
    private List<CategoryInfo> datas;

    @Override
    protected int load() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        datas = categoryProtocol.load(0);
        return checkDatas(datas);//LoadingPage.STATU_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new CategoryAdapter(datas, listView));
        return listView;
    }


    class CategoryAdapter extends DefaultAdapter<CategoryInfo> {

        private int position;

        public CategoryAdapter(List<CategoryInfo> datas, ListView listView) {
            super(datas, listView);
        }


        /**
         * 获取 listview 行视图的种类数量
         *
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;//3
        }

        /**
         * 返回条目类型
         *
         * @param position
         * @return
         */
        @Override
        protected int getInnerItemViewType(int position) { // 0,1,2
            if (datas.get(position).isTitle) {
                return ITEM_TITLE;
            }
            return super.getInnerItemViewType(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.position = position;
            return super.getView(position, convertView, parent);
        }

        @Override
        protected BaseHolder<CategoryInfo> getHolder() {

            if (getItemViewType(position) == ITEM_TITLE) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryContentHolder();
            }
        }

        @Override
        protected boolean hasMore() {
            return false;// false 不会显示加载更多的条目   不会调用loadMore 加载更多数据
        }
    }


}
