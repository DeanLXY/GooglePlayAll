package itcast.zz16.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itcast.zz16.googleplay.bean.CategoryInfo;
import itcast.zz16.googleplay.utils.LogUtil;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file CategoryProtocol
 * @create_time 2016/8/26 0026
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    protected List<CategoryInfo> parseJson(String json) {
        LogUtil.d("%s", json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject;
            JSONArray infosArray;
            CategoryInfo categoryInfo = null;
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                //获取标题信息
                String title = jsonObject.optString("title");
                categoryInfo = new CategoryInfo();
                categoryInfo.title = title;
                categoryInfo.isTitle = true;//是标题
                categoryInfos.add(categoryInfo);

                infosArray = jsonObject.optJSONArray("infos");
                for (int j = 0; j < infosArray.length(); j++) {
                    jsonObject = infosArray.optJSONObject(j);
                    String name1 = jsonObject.optString("name1");
                    String name2 = jsonObject.optString("name2");
                    String name3 = jsonObject.optString("name3");
                    String url1 = jsonObject.optString("url1");
                    String url2 = jsonObject.optString("url2");
                    String url3 = jsonObject.optString("url3");
                    categoryInfo = new CategoryInfo(name1, name2, name3, url1, url2, url3);
                    categoryInfos.add(categoryInfo);
                }
            }
            return  categoryInfos;


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected String getKey() {
        return "category";
    }
}
