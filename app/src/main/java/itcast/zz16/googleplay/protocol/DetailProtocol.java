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
 * @file DetailProtocol
 * @create_time 2016/8/24 0024
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class DetailProtocol extends BaseProtocol<AppInfo> {
    private String packageName ;

    public DetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected AppInfo parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            long id = jsonObject.optLong("id");
            String name = jsonObject.optString("name");
            String packageName = jsonObject.optString("packageName");
            String iconUrl = jsonObject.optString("iconUrl");
            double stars = jsonObject.optDouble("stars");
            long size = jsonObject.optLong("size");
            String downloadUrl = jsonObject.optString("downloadUrl");
            String des = jsonObject.optString("des");
            String author = jsonObject.optString("author");
            String date = jsonObject.optString("date");
            String downloadNum = jsonObject.optString("downloadNum");
            String version = jsonObject.optString("version");


            //截图信息
            List<String> screens = new ArrayList<>();//所有的截图信息
            if (jsonObject.has("screen")) {


                JSONArray screenArray = jsonObject.optJSONArray("screen");
                for (int i = 0; i < screenArray.length(); i++) {
                    String screen = screenArray.optString(i);
                    screens.add(screen);
                }
            }

            JSONArray safeArray = jsonObject.optJSONArray("safe");
            JSONObject safeObject = null;

            List<String> safeUrls = new ArrayList<>();//所有安全扫描结果的图片url
            List<String> safeDesUrls= new ArrayList<>();//所有安全扫描描述的图片url
            List<String> safeDeses= new ArrayList<>();//所有安全扫描描述文本
            List<Integer> safeDesColors= new ArrayList<>();//所有安全扫描描述文本颜色

            for (int i = 0; i < safeArray.length(); i++) {
                safeObject = safeArray.optJSONObject(i);
                String safeUrl = safeObject.optString("safeUrl");
                String safeDesUrl = safeObject.optString("safeDesUrl");
                String safeDes = safeObject.optString("safeDes");
                int safeDesColor = safeObject.optInt("safeDesColor");

                safeUrls.add(safeUrl);
                safeDesUrls.add(safeDesUrl);
                safeDeses.add(safeDes);
                safeDesColors.add(safeDesColor);

            }

            AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl,
                    des, author, date, downloadNum, version, screens, safeUrls, safeDesUrls, safeDeses, safeDesColors);
            return appInfo;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getExtrasParams() {
        return "&packageName=" + packageName;
    }

    @Override
    protected String getKey() {
        return "detail";
    }
}
