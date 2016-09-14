package itcast.zz16.googleplay.utils;

import android.os.Environment;

import java.io.File;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file FileUtils
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * 统一管理所有的缓存目录
 */
public class FileUtils {

    public static final String CACHE = "cache";
    private static final String ROOT = "GooglePlayz16";//根目录
    public static final String ICON = "icon";

    // /mnt/sdcard/GooglePlayz16/cache
    // /data/data/包名/cache/cache
    public static File getDir(String dir) {

        StringBuilder stringBuilder = new StringBuilder();

        if (isSDCardAvailable()) {
            stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());//  /mnt/sdcard
            stringBuilder.append(File.separator); //   /mnt/sdcard/
            stringBuilder.append(ROOT);//   /mnt/sdcard/GooglePlayz16
            stringBuilder.append(File.separator); //  /mnt/sdcard/GooglePlayz16/
            stringBuilder.append(dir);//  /mnt/sdcard/GooglePlayz16/cache
        } else {
//            /data/data/包名/cache/cache
            stringBuilder.append(UIUtils.getContext().getCacheDir().getAbsolutePath());//  /data/data/包名/cache
            stringBuilder.append(File.separator);  ///data/data/包名/cache/
            stringBuilder.append(dir);///data / data / 包名 / cache / cache
        }

        File file = new File(stringBuilder.toString());
        //目录不存在 应该创建  不能是一个文件
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }


        return file;
    }

    /**
     * 判断sd卡是否可用
     * @return
     */
    private static boolean isSDCardAvailable() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//sd卡可用
    }

    /**
     * 获取数据缓存目录
     *
     * @return
     */
    public static File getCache() {
        return getDir(CACHE);
    }

    /**
     * 维护图片缓存的目录
     * @return
     */
    public static  File getIcon(){
        return getDir(ICON);
    }
}
