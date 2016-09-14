package itcast.zz16.googleplay.bean;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file UserInfo
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class UserInfo {
//    {name:'传智黄盖',email:'huanggai@itcast.cn',url:'image/user.png'}
    public String name;
    public String email;
    public String url;

    public UserInfo(String name, String email, String url) {
        this.name = name;
        this.email = email;
        this.url = url;
    }
}
