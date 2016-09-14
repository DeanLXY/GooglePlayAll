package itcast.zz16.googleplay.bean;

import java.util.List;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file AppInfo
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * 内省操作
 */
public class AppInfo {
    public long id;
    public String name;
    public String packageName;
    public String iconUrl; // iconUrl: "true"
    public double stars;
    public long size;//String万能
    public String downloadUrl;
    public String des;

    ///////////////////////////////////////////////////////////////////////////
    // 详情界面的字段
    ///////////////////////////////////////////////////////////////////////////

    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public List<String> screens;//所有的截图信息
    public List<String> safeUrls ;//所有安全扫描结果的图片url
    public List<String> safeDesUrls ;//所有安全扫描描述的图片url
    public List<String> safeDeses ;//所有安全扫描描述文本
    public List<Integer> safeDesColors ;//所有安全扫描描述文本颜色


    public AppInfo(long id, String name, String packageName, String iconUrl, double stars, long size, String downloadUrl, String des) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
    }

    public AppInfo(long id, String name, String packageName, String iconUrl, double stars, long size, String downloadUrl, String des, String author, String date, String downloadNum, String version, List<String> screens, List<String> safeUrls, List<String> safeDesUrls, List<String> safeDeses, List<Integer> safeDesColors) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
        this.author = author;
        this.date = date;
        this.downloadNum = downloadNum;
        this.version = version;
        this.screens = screens;
        this.safeUrls = safeUrls;
        this.safeDesUrls = safeDesUrls;
        this.safeDeses = safeDeses;
        this.safeDesColors = safeDesColors;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
