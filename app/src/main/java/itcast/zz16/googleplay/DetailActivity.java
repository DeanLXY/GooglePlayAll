package itcast.zz16.googleplay;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import itcast.zz16.googleplay.fragments.DetailFragment;

public class DetailActivity extends BaseActivity {

    private String packageName;

    @Override
    protected void init() {
        super.init();
        packageName = getIntent().getStringExtra("packageName");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_detail);

        //activity 怎么给Fragment传递数据

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();// map
        bundle.putString("packageName",packageName);
        fragment.setArguments(bundle);
        //fragment 替换 帧布局
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        //替换ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
