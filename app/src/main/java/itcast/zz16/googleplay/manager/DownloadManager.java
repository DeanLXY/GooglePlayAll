package itcast.zz16.googleplay.manager;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.db.DownloadDbHelper;
import itcast.zz16.googleplay.db.DownloadInfo;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.IOUtils;
import itcast.zz16.googleplay.utils.LogUtil;
import itcast.zz16.googleplay.utils.UIUtils;

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
 * <p>
 * ======================
 */
public class DownloadManager {
    /**
     * 默认
     */
    public static final int STATE_NONE = 0;
    /**
     * 等待
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSE = 3;
    /**
     * 错误
     */
    public static final int STATE_ERROR = 4;
    /**
     * 下载完成
     */
    public static final int STATE_DOWNLOED = 5;

    private DownloadManager() {
    }

    private static DownloadManager instance = new DownloadManager();

    public static DownloadManager getInstance() {
        return instance;
    }

    private static Map<Long, DownloadTask> taskMap = new HashMap<>();
    private static Map<Long, DownloadInfo> downloadInfoMap = new HashMap<>();
    //下载管理
    private List<DownloadObserver> mObservers = new ArrayList<DownloadObserver>();

    public void addDownloadTask(AppInfo appInfo) {
        //1.先从缓存中读取下载任务信息
        DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.id);
        //2. 如果缓存中没有下载任务信息  就从 数据库中获取 任务信息
        if (downloadInfo == null)
            downloadInfo = DownloadDbHelper.getInstance().getDownloadInfo(appInfo.id);
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo().clone(appInfo);
            downloadInfoMap.put(appInfo.id, downloadInfo);
        }

        if (downloadInfo.getDownloadState() == STATE_NONE
                || downloadInfo.getDownloadState() == STATE_PAUSE
                || downloadInfo.getDownloadState() == STATE_ERROR) {
            // 下载之前，把状态设置为STATE_WAITING，
            // 因为此时并没有产开始下载，只是把任务放入了线程池中，
            // 当任务真正开始执行时，才会改为STATE_DOWNLOADING
            downloadInfo.setDownloadState(STATE_WAITING);
            // 通知更新界面
            notifyDownloadStateChanged(downloadInfo);
            DownloadTask task = new DownloadTask(downloadInfo);
            taskMap.put(downloadInfo.getId(), task);
            ThreadPoolManager.getInstance().createLongThreadPool().execute(task);
        }

    }

    /**
     * 取消下载，逻辑和暂停类似，只是需要删除已下载的文件
     */
    public synchronized void cancel(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = downloadInfoMap.get(appInfo.id);// 找出下载信息
        if (info != null) {// 修改下载状态并删除文件
            info.setDownloadState(STATE_NONE);
            notifyDownloadStateChanged(info);
            info.setCurrentSize(0L);
            File file = new File(info.getPath());
            file.delete();
        }
    }

    /**
     * 暂停下载
     */
    public synchronized void pause(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = downloadInfoMap.get(appInfo.id);
        if (info != null) {// 修改下载状态
            info.setDownloadState(STATE_PAUSE);
            notifyDownloadStateChanged(info);
        }
    }

    /**
     * 停止下载任务
     *
     * @param appInfo
     */
    private void stopDownload(AppInfo appInfo) {
        DownloadTask task = taskMap.remove(appInfo.id);
        if (task != null) {
            ThreadPoolManager.getInstance().createLongThreadPool().cancel(task);
        }
    }

    /**
     * 安装应用
     */
    public synchronized void install(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = downloadInfoMap.get(appInfo.id);// 找出下载信息
        if (info != null) {// 发送安装的意图
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(installIntent);
        }
        notifyDownloadStateChanged(info);
    }

    /**
     * 注册观察者
     */
    public void registerObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (!mObservers.contains(observer)) {
                mObservers.add(observer);
            }
        }
    }

    /**
     * 反注册观察者
     */
    public void unRegisterObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                mObservers.remove(observer);
            }
        }
    }

    /**
     * 当下载状态发送改变的时候回调
     */
    public void notifyDownloadStateChanged(DownloadInfo info) {
        DownloadDbHelper.getInstance().insertOrUpdate(info);
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadStateChanged(info);
            }
        }
    }

    /**
     * 当下载进度发送改变的时候回调
     */
    public void notifyDownloadProgressed(DownloadInfo info) {
        DownloadDbHelper.getInstance().insertOrUpdate(info);
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadProgressed(info);
            }
        }
    }

    public class DownloadTask implements Runnable {

        private DownloadInfo info;

        public DownloadTask(DownloadInfo info) {

            this.info = info;
        }

        @Override
        public void run() {
            info.setDownloadState(STATE_DOWNLOADING);
            notifyDownloadStateChanged(info);
            File file = new File(info.getPath());// 获取下载文件
            //重新下载的条件
            HttpHelper.HttpResult httpResult = null;
            InputStream inputStream = null;
            FileOutputStream fos = null;
            if (info.getCurrentSize() == 0 || !file.exists() || file.length() != info.getAppSize()) {
                LogUtil.d("%s","开始下载");
                info.setCurrentSize(0L);
                HttpHelper httpHelper = new HttpHelper(
                        HttpHelper.BASEURL + "/download?name=" + info.getUrl());
                httpResult = httpHelper.download();
                LogUtil.d("%s","开始下载");
            } else {
                // 断点下载
                LogUtil.d("%s","开始下载>>>断点下载");
                HttpHelper httpHelper = new HttpHelper(
                        HttpHelper.BASEURL + "/download?name=" + info.getUrl()
                                + "&range=" + info.getCurrentSize());
                httpResult = httpHelper.download();
            }

            if (httpResult == null
                    || (inputStream = httpResult.getInputStream()) == null) {
                //下载失败
                info.setDownloadState(STATE_ERROR);
                notifyDownloadStateChanged(info);
            } else {
                try {
                    //是否继续 断点下载
                    fos = new FileOutputStream(file, true);
                    int count = -1;
                    byte[] buffer = new byte[4096];
                    while ((count = inputStream.read(buffer)) != -1) {
                        fos.write(buffer);
                        fos.flush();
                        info.setCurrentSize(info.getCurrentSize() + count);
                        notifyDownloadProgressed(info);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //释放流资源
                    IOUtils.close(fos);
                    IOUtils.close(inputStream);
                }

                // 判断进度是否和App相等
                if (info.getCurrentSize().longValue() == info.getAppSize().longValue()) {
                    info.setDownloadState(STATE_DOWNLOED);
                    notifyDownloadStateChanged(info);
                } else if (info.getDownloadState().intValue() == STATE_PAUSE) {
                    notifyDownloadStateChanged(info);
                } else {
                    info.setDownloadState(STATE_ERROR);
                    notifyDownloadStateChanged(info);
                    info.setCurrentSize(0L);
                    file.delete();
                }

                taskMap.remove(this);
            }
        }
    }

//    private List<DownloadObserver> observers = new ArrayList<>();

   /* public void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer can't be null, please check it again!");
        }

        if (observers.contains(observer)) {
            throw new IllegalStateException("download task ");
        }

        observers.add(observer);
    }*/

    public interface DownloadObserver {

        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgressed(DownloadInfo info);
    }
}
