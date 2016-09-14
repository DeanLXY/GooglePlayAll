package itcast.zz16.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itcast.zz16.googleplay.bean.SubjectInfo;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file SubjectProtocol
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * // 1. 请求数据2.缓存 3.复用缓存 4解析
 */
public class SubjectProtocol  extends  BaseProtocol<List<SubjectInfo>>{


    @Override
    protected List<SubjectInfo> parseJson(String json) {
        //[ JsonArray
        try {
            JSONArray jsonArray = new JSONArray(json);

            JSONObject jsonObject = null;
            SubjectInfo subjectInfo = null;
            List<SubjectInfo> subjectInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                String des = jsonObject.optString("des");
                String url = jsonObject.optString("url");

                subjectInfo = new SubjectInfo(des, url);
                subjectInfos.add(subjectInfo);
            }

            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected String getKey() {
        return "subject";
    }
}
