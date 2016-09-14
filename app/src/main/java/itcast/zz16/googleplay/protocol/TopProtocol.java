package itcast.zz16.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file TopProtocol
 * @create_time 2016/8/27 0027
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class TopProtocol extends BaseProtocol<List<String>>{
    @Override
    protected List<String> parseJson(String json) {
        //[ JsonArray
        // json --->JsonArray
        try {
            List<String> strings = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String key = jsonArray.optString(i);
                strings.add(key);
            }
            return  strings;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //	// http://127.0.0.1:8090/hot
    @Override
    protected String getKey() {
        return "hot";
    }
}
