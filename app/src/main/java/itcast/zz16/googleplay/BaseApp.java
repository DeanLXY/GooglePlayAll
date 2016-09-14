package itcast.zz16.googleplay;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import itcast.zz16.googleplay.db.DownloadDbHelper;
import itcast.zz16.googleplay.utils.FileUtils;
import itcast.zz16.googleplay.utils.ImageUtils;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseApp
 * @create_time 2016/8/20 0020
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class BaseApp extends Application {
    private static BaseApp instance;

    /**
     * 获取上下文实例对象
     *
     * @return
     */
    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ImageUtils.init(this);

        //初始化数据库
        DownloadDbHelper.getInstance().init(this);
    }

}
