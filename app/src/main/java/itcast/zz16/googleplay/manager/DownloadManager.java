package itcast.zz16.googleplay.manager;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file ${FILE_NAME}
 * @create_time 2016/9/14 0014
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DownloadManager {

    private DownloadManager() {
    }

    private static DownloadManager instance = new DownloadManager();

    public static DownloadManager getInstance() {
        return instance;
    }

    public void addDownloadTask(){

    }
}
