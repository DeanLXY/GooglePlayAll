package itcast.zz16.googleplay.utils;

import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file ImageUtils
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class ImageUtils {
    /**
     * 修改picaso 缓存路径
     *
     * @param context
     */
    public static void init(Context context) {
        // 修改picasso 默认缓存目录

        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(FileUtils.getIcon()))
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
