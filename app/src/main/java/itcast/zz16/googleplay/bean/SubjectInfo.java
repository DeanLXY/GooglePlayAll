package itcast.zz16.googleplay.bean;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file SubjectInfo
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class SubjectInfo {
//    "des": "一周新锐游戏精选",
//            "url": "image/recommend_01.jpg"

    public String des;
    public String url;

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
