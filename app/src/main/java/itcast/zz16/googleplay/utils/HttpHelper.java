package itcast.zz16.googleplay.utils;


import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file HttpHelper
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p>
 * ======================
 */
public class HttpHelper {
    public static final String BASEURL = "http://127.0.0.1:8090";

    private String url;

    public HttpHelper(String url) {
        this.url = url;
    }

    /**
     * 同步get请求
     *
     * @return
     */
    public String getSync() {

        try {
            Response response = OkHttpUtils
                    .get()
                    .url(url)
                    .build()
                    .execute();
            ;//同步任务
            if (response.isSuccessful()) { // 200
                return response.body().string(); // 返回json数据
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public HttpResult download() {
        try {
            LogUtil.d("%s","download");
            Response response = OkHttpUtils
                    .get()
                    .url(url)
                    .build()
                    .execute();
            HttpResult httpResult = null;
            LogUtil.d("%s","请求"+response.code());
            if (response.isSuccessful()) {
                httpResult = new HttpResult(response, url);
            }
            return httpResult;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("%s","download>>>"+e.getMessage());
        }
        return null;
    }


    public class HttpResult {
        private InputStream mInputStream;

        private String mStr;
        private String mUrl;
        private Response mResponse;

        public HttpResult(Response mResponse, String mUrl) {

            this.mResponse = mResponse;
            this.mUrl = mUrl;
//            this.mInputStream = mResponse.body().byteStream();

        }


        public int getCode() {
            return mResponse.code();
        }

        public String getUrl() {
            return this.mUrl;
        }


        public InputStream getInputStream() {
            this.mInputStream = mResponse.body().byteStream();
            return this.mInputStream;
        }

        public String getString(){
            try {
                this.mStr = mResponse.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mStr;
        }
    }
}
