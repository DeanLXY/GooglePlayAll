package itcast.zz16.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itcast.zz16.googleplay.bean.AppInfo;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file HomeProtocol
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * // 1. 请求服务器数据 2. 缓存  3.复用缓存  4.解析
 */
public class HomeProtocol extends BaseProtocol<List<AppInfo>> {

    private List<String> pictures;

    /**
     * 获取图片的url
     * @return
     */
    public List<String> getPictures() {
        return pictures;
    }

    /**
     * 解析json
     * Gson  1/6
     *
     * @param json
     */
    protected List<AppInfo> parseJson(String json) {
        //{ JsonObject
        //[ JsonArray
        try {
            JSONObject jsonObject = new JSONObject(json);
            //首页轮播图 url
            JSONArray pictureArray = jsonObject.optJSONArray("picture");

            pictures = new ArrayList<>();
            for (int i = 0; i < pictureArray.length(); i++) {
                String url = pictureArray.optString(i);
                pictures.add(url);
            }


            JSONArray listArray = jsonObject.optJSONArray("list");
            JSONObject listObject = null;
            List<AppInfo> appInfos = new ArrayList<>();
            AppInfo appInfo = null;
            for (int i = 0; i < listArray.length(); i++) {
                listObject = listArray.optJSONObject(i);
                long id = listObject.optLong("id");
                String name = listObject.optString("name");
                String packageName = listObject.optString("packageName");
                String iconUrl = listObject.optString("iconUrl");
                double stars = listObject.optDouble("stars");
                long size = listObject.optLong("size");
                String downloadUrl = listObject.optString("downloadUrl");
                String des = listObject.optString("des");
                appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfos.add(appInfo);
            }

            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 请求网络的关键字
     *
     * @return
     */
    @Override
    protected String getKey() {
        return "home";
    }
}
