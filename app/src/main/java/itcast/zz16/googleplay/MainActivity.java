package itcast.zz16.googleplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import itcast.zz16.googleplay.fragments.BaseFragment;
import itcast.zz16.googleplay.fragments.FragmentFactory;
import itcast.zz16.googleplay.holder.MenuHolder;
import itcast.zz16.googleplay.utils.LogUtil;
import itcast.zz16.googleplay.utils.ToastUtils;
import itcast.zz16.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "GooglePlayz16";
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] tabNames;
    private FrameLayout flMenu;

    @Override
    protected void init() {
        super.init();

        tabNames = UIUtils.getStringArray(R.array.tab_names);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_main);
        //获取抽屉对象
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        // 修改下划线颜色
        pagerTabStrip.setTabIndicatorColorResource(R.color.indicatorcolor);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // 监听ViewPager切换 重新请求服务器数据
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BaseFragment fragment = FragmentFactory.create(position);
                fragment.show();// 请求服务器数据

            }
        });

        // %s 字符串
        //%d 数字
        LogUtil.d("%s", "我是日志,能看到我吗?");
        LogUtil.e("%s", "我是日志,能看到我吗?");
        //菜单
        flMenu = (FrameLayout) findViewById(R.id.fl_menu);
        MenuHolder menuHolder = new MenuHolder();// --->initView 创建view对象 初始化操作
        //menuHolder.setData();

        flMenu.addView(menuHolder.getContentView());

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        // 替换actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        // 显示菜单
        actionBar.setDisplayHomeAsUpEnabled(true);
        //ActionBarDrawerToggle  让ActionBar 和 drawerLayout做联动

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);

        //actionbar 同步
        drawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用menu /xxx.xml
        getMenuInflater().inflate(R.menu.menu_main, menu);


        //获取searchView findviewbyid    alt + 回车
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    // 响应ActionBar的按钮点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //处理actionbar 和drawerlayout 点击的联动
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    // 文本内容改变调用
    @Override
    public boolean onQueryTextChange(String newText) {
        ToastUtils.showToast(this, newText);
        return false;
    }

    // 回车调用
    @Override
    public boolean onQueryTextSubmit(String query) {
        ToastUtils.showToast(this, query);
        return false;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //返回ViewPager的页面数量
        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.create(position);
        }

        //获取 页面的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }


}
