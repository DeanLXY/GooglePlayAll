package itcast.zz16.googleplay.bean;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file CategoryInfo
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class CategoryInfo {
    public String name1;
    public String name2;
    public String name3;
    public String url1;
    public String url2;
    public String url3;

    //标题
    public String title;
    public boolean isTitle = false;

    public CategoryInfo() {
    }

    public CategoryInfo(String name1, String name2, String name3, String url1, String url2, String url3) {
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                '}';
    }
}
