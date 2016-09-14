package itcast.zz16.googleplay.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file IOUtils
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class IOUtils {

    /**
     * 关闭流资源
     * @param closeable
     */
    public static  void close(Closeable closeable){
        if (closeable!= null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
