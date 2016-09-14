package itcast.zz16.googleplay.protocol;

import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import itcast.zz16.googleplay.bean.AppInfo;
import itcast.zz16.googleplay.utils.FileUtils;
import itcast.zz16.googleplay.utils.HttpHelper;
import itcast.zz16.googleplay.utils.IOUtils;
import itcast.zz16.googleplay.utils.LogUtil;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file BaseProtocol
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 * 1. 泛型声明  类上 属性和方法都可以使用
 *  2. 方法上声明   只有本方法可以用
 *   List<AppInfo>  --->T   返回什么 泛型就写什么
 */
public abstract class BaseProtocol<T> {

    /**
     * 请求数据
     *
     * @param index
     */
    public T load(int index) {
        SystemClock.sleep(2000);
        String json = loadFromLocal(index);//获取缓存数据
        // 如果缓存中没有数据 请求服务器数据 重新将数据缓存到本地
        if (json == null) {
            json = loadFromServer(index);
            if (json != null) {
                //缓存数据
                save2Local(index, json);
            }
        } else {
            LogUtil.d("%s", "复用了缓存数据...");
        }

        //  解析json
        if (json != null) {
            return parseJson(json);
        }
        return null;

    }

    /**
     * 请求服务器数据
     * HttpClient
     * HttpUrlConnection
     * <p/>
     * Volley <=22
     * OkHttp 效率高
     * Retrofit 返回java对象
     * NoHttp 用法简单
     *
     * @param index
     * @return
     */
    private String loadFromServer(int index) {
        // http://127.0.0.1:8090/home?index=0
        // http://127.0.0.1:8090/subject?index=0 packageName
        HttpHelper httpHelper = new HttpHelper(HttpHelper.BASEURL + "/" + getKey() + "?index=" + index+getExtrasParams());
        String json = httpHelper.getSync();
        LogUtil.d("%s", "请求服务器成功");
        LogUtil.d("%s", json);
        return json;
    }

    /**
     * 额外的参数
     * @return
     */
    protected String getExtrasParams() {
        return "";
    }


    /**
     * 缓存数据
     * 1. json
     * 2. 缓存到数据库
     * <p/>
     * 1. md5
     * 2. 过期时间
     *
     * @param index
     * @param json
     */
    private void save2Local(int index, String json) {

        //  1, 缓存json 数据
        //  第一行添加一个过期时间(模拟)

        // 读取第一行过期时间 , 如果当前时间>过期时间 过期 请求服务器数据
        //如果当前时间< 过期事件  没有过期  复用缓存数据
        File dir = FileUtils.getCache();//sd卡中

        File file = new File(dir, getKey() +"_"+getExtrasParams()+ "_" + index);

        // 1. 字节流 任何文件
        // 2. 字符流 Writer  Reader
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
//            fw.write(System.currentTimeMillis()+100*1000+"");//添加过期时间
//            fw.write("\r\n");//换行  BufferedWriter
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis() + 100 * 1000 + "");
            bw.newLine();//换行
            //将json写到文件中
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放流
            IOUtils.close(bw);
            IOUtils.close(fw);
        }

    }

    /**
     * 获取缓存数据
     *
     * @param index
     * @return
     */
    private String loadFromLocal(int index) {
        // http://127.0.0.1:8090/home?index=0
        File dir = FileUtils.getCache();//sd卡中

        File file = new File(dir,  getKey() +"_"+getExtrasParams() + "_" + index);
        FileReader fr = null;
        BufferedReader br = null;
        StringWriter sw = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            //读取过期事件
            long outofDate = Long.parseLong(br.readLine());
            //如果当前时间>过期 过期了
            if (System.currentTimeMillis() > outofDate) {
                //过去
                return null;
            } else {
                // byteArrayoutputStream 读到内存中
                // StringWriter  sw 将字符串独读到内存中
                String line = null;
                sw = new StringWriter();
                while ((line = br.readLine()) != null) {
                    //将读到的行写到内存中
                    sw.write(line);
                }
                return sw.toString();//将内存中的数据返回
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            IOUtils.close(sw);
            IOUtils.close(br);
            IOUtils.close(fr);
        }


        return null;
    }


    /**
     * 解析json
     * Gson  1/6
     *
     * @param json
     */
    protected abstract T parseJson(String json);

    /**
     * 请求网络的关键字
     * // http://127.0.0.1:8090/home?index=0  home
     * // http://127.0.0.1:8090/subject?index=0   subject
     *
     * @return
     */
    protected abstract String getKey();
}
