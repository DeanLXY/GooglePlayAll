package itcast.zz16.googleplay.db;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file DownloadDbHelper
 * @create_time 2016/9/14 0014
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
import de.greenrobot.dao.query.QueryBuilder;
import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.UIUtils;

public class DownloadDbHelper {

    private static DownloadInfoDao downloadInfoDao;

    private DownloadDbHelper() {
    }

    private static DownloadDbHelper instance = new DownloadDbHelper();

    public static DownloadDbHelper getInstance() {
        return instance;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "download.db", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        downloadInfoDao = session.getDownloadInfoDao();
    }

    /**
     * 插入或者更新数据
     * 1.如果有下载任务就更新 如果没有就插入
     *
     * @param downloadInfo
     */
    public void insertOrUpdate(DownloadInfo downloadInfo) {

        DownloadInfo temp = getDownloadInfo(downloadInfo.getId());
        if (temp == null) {
            downloadInfoDao.insert(downloadInfo);
        } else {
            downloadInfoDao.update(downloadInfo);
        }
    }


    /**
     * 根据id获取元素
     *
     * @param id
     * @return
     */
    public DownloadInfo getDownloadInfo(@NonNull Long id) {
        QueryBuilder<DownloadInfo> builder
                = downloadInfoDao
                .queryBuilder()
                .where(DownloadInfoDao.Properties.Id.eq(id));
        if (builder.list().size() == 0) {
            return null;
        } else if (builder.list().size() > 1) {
            throw new IllegalStateException("id not unique primary key!");
        }
        return builder.list().get(0);
    }

    /**
     * 安装应用
     */
    public synchronized void install(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = getDownloadInfo(appInfo.id);// 找出下载信息
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
     * 停止任务
     *
     * @param appInfo
     */
    private void stopDownload(AppInfo appInfo) {

    }

    public void notifyDownloadStateChanged(DownloadInfo info) {

    }


}
