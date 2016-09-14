package itcast.zz16.googleplay;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file CustomGenerator
 * @create_time 2016/9/14 0014
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class CustomGenerator {
    public static void main(String[] args) {

        Schema schema = new Schema(1,"itcast.zz16.googleplay.db");
        Entity downloadInfo = schema.addEntity("DownloadInfo");
//        private String appName;// 软件的名字
//        private long appSize=0; // app的size
//        private long currentSize=0;// 当前下载的大小
//        private int downloadState=0;//下载的状态
//        private String url;//下载的地址
//        private String path;// 保存的路径
        downloadInfo.addIdProperty();
        downloadInfo.addStringProperty("appName");
        downloadInfo.addLongProperty("appSize");
        downloadInfo.addLongProperty("currentSize");
        downloadInfo.addIntProperty("downloadState");
        downloadInfo.addStringProperty("url");
        downloadInfo.addStringProperty("path");

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


