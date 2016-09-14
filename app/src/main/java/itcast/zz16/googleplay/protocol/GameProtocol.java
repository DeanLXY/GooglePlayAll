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
 * @file GameProtocol
 * @create_time 2016/8/23 0023
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class GameProtocol  extends BaseProtocol<List<AppInfo>>{
    @Override
    protected List<AppInfo> parseJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = null;
            AppInfo appInfo = null;
            List<AppInfo> appInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                long id = jsonObject.optLong("id");
                String name = jsonObject.optString("name");
                String packageName = jsonObject.optString("packageName");
                String iconUrl = jsonObject.optString("iconUrl");
                double stars = jsonObject.optDouble("stars");
                long size = jsonObject.optLong("size");
                String downloadUrl = jsonObject.optString("downloadUrl");
                String des = jsonObject.optString("des");
                appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfos.add(appInfo);
            }

            return appInfos;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected String getKey() {
        return "game";
    }
}
