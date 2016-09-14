package itcast.zz16.googleplay.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import itcast.zz16.googleplay.bean.UserInfo;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file UserProtocol
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class UserProtocol extends BaseProtocol<UserInfo> {
    @Override
    protected UserInfo parseJson(String json) {
//        {name:'传智黄盖',email:'huanggai@itcast.cn',url:'image/user.png'}
        try {
            JSONObject jsonObject = new JSONObject(json);
            String name = jsonObject.optString("name");
            String email = jsonObject.optString("email");
            String url = jsonObject.optString("url");
            UserInfo userInfo = new UserInfo(name, email, url);
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //http://127.0.0.1:8090/user
    @Override
    protected String getKey() {
        return "user";
    }
}
